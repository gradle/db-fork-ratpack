/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.handling

import ratpack.test.internal.RatpackGroovyDslSpec

import javax.inject.Provider

class ContextProviderSpec extends RatpackGroovyDslSpec {

  static class Thing {
    String value
  }

  def "can use context provider"() {
    when:
    Provider<Context> p = null

    handlers {
      handler {
        p = provider
        next()
      }
      register(new Thing(value: "1")) {
        handler {
          response.headers.set("L1", p.get().get(Thing).value)
          next()
        }
        register(new Thing(value: "2")) {
          handler {
            background {
              response.headers.set("L2", p.get().get(Thing).value)
            } then {
              p.get().next()
            }
          }
        }
        register(new Thing(value: "3")) {
          get {
            render p.get().get(Thing).value
          }
        }
      }
    }

    then:
    getText() == "3"
    response.header("L1") == "1"
    response.header("L2") == "2"
  }

}
