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

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертер в строку или булево из атрибута value
 */
public class CRSValueConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // none
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var value = reader.getAttribute("value");
    if (context.getRequiredType() == boolean.class || context.getRequiredType() == Boolean.class) {
      return Boolean.parseBoolean(value);
    }
    return value;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == String.class || type == boolean.class || type == Boolean.class;
  }

}
