/*
 * Copyright 2009 Last.fm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fm.last.moji.tracker.impl;

import java.util.HashMap;
import java.util.Map;

enum ResponseStatus {
  OK("OK"), ERROR("ERR");

  private static Map<String, ResponseStatus> wireCodeToVal = new HashMap<String, ResponseStatus>();

  static {
    for (ResponseStatus status : ResponseStatus.values()) {
      wireCodeToVal.put(status.value(), status);
    }
  }

  private final String code;

  private ResponseStatus(String code) {
    this.code = code;
  }

  String value() {
    return code;
  }

  static ResponseStatus valueOfCode(String code) {
    return wireCodeToVal.get(code);
  }

}
