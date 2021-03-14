/*
 * This file is a part of CRS-API for java.
 *
 * Copyright © 2021
 * Tymko Oleg <olegtymko@yandex.ru> and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.otymko.bsl.crs_api.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageConvertorTest {

  @Test
  void decodeErrorMessage() {
    var message = "\n" +
      "77u/ew0KezNjY2IyNTE4LTk2MTYtNDQ0NS1hYWE3LTIwMDQ4ZmVhZDE3NCwi0J7R\n" +
      "iNC40LHQutCwINCw0YPRgtC10L3RgtC40YTQuNC60LDRhtC40Lgg0LIg0YXRgNCw\n" +
      "0L3QuNC70LjRidC1INC60L7QvdGE0LjQs9GD0YDQsNGG0LjQuCENCtCf0YDQvtCy\n" +
      "0LXRgNGM0YLQtSDQv9GA0LDQstC40LvRjNC90L7RgdGC0Ywg0LLQstC10LTQtdC9\n" +
      "0L3QvtCz0L4g0LjQvNC10L3QuCDQv9C+0LvRjNC30L7QstCw0YLQtdC70Y8g0Lgg\n" +
      "0L/QsNGA0L7Qu9GPLiJ9LDQsDQp7ImZpbGU6Ly8vaG9tZS91c3IxY3Y4Ly4xY3Y4\n" +
      "L3JlcG8vbWFpbmNyIiwwfSwiIn0=";

    var decodeMessage = ErrorMessageConvertor.decodeMessage(message);
    assertThat(decodeMessage).isNotEmpty();
  }

}