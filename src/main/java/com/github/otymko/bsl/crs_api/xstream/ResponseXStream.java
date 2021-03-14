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
package com.github.otymko.bsl.crs_api.xstream;

import com.github.otymko.bsl.crs_api.format.CallExceptionResponse;
import com.github.otymko.bsl.crs_api.format.CallReturnResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;

/**
 * Кастомный XStream
 */
public class ResponseXStream extends XStream {

  public ResponseXStream() {
    autodetectAnnotations(true);
    ignoreUnknownElements();
    setMode(XStream.NO_REFERENCES);
    XStream.setupDefaultSecurity(this);
    addPermission(NoTypePermission.NONE);
    addPermission(new WildcardTypePermission(new String[]{"com.github.otymko.bsl.crs_api.format.**"}));

    registerAliases();
  }

  @Override
  protected void setupConverters() {
    registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
    registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
    registerConverter(new StringConverter(), PRIORITY_NORMAL);
    registerConverter(new DateConverter(), PRIORITY_NORMAL);
    registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new ReflectionConverter(getMapper(), getReflectionProvider()), PRIORITY_VERY_LOW);
  }

  private void registerAliases() {
    alias("crs:call_return", CallReturnResponse.class);
    alias("crs:call_exception", CallExceptionResponse.class);
  }

}
