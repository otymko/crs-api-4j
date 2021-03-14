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
package com.github.otymko.bsl.crs_api.format;

import com.github.otymko.bsl.crs_api.xstream.XStreamHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormatTest {

  @Test
  void testCallException() {
    var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      "<crs:call_exception xmlns:crs=\"http://v8.1c.ru/8.2/crs\" clsid=\"3ccb2518-9616-4445-aaa7-20048fead174\">\n" +
      "77u/ew0KezNjY2IyNTE4LTk2MTYtNDQ0NS1hYWE3LTIwMDQ4ZmVhZDE3NCwi0J7R\n" +
      "iNC40LHQutCwINCw0YPRgtC10L3RgtC40YTQuNC60LDRhtC40Lgg0LIg0YXRgNCw\n" +
      "0L3QuNC70LjRidC1INC60L7QvdGE0LjQs9GD0YDQsNGG0LjQuCENCtCf0YDQvtCy\n" +
      "0LXRgNGM0YLQtSDQv9GA0LDQstC40LvRjNC90L7RgdGC0Ywg0LLQstC10LTQtdC9\n" +
      "0L3QvtCz0L4g0LjQvNC10L3QuCDQv9C+0LvRjNC30L7QstCw0YLQtdC70Y8g0Lgg\n" +
      "0L/QsNGA0L7Qu9GPLiJ9LDQsDQp7ImZpbGU6Ly8vaG9tZS91c3IxY3Y4Ly4xY3Y4\n" +
      "L3JlcG8vbWFpbmNyIiwwfSwiIn0=</crs:call_exception>";

    var value = (CallExceptionResponse) XStreamHelper.readFromString(xml);

    assertThat(value.getClsid()).hasToString("3ccb2518-9616-4445-aaa7-20048fead174");
    assertThat(value.getMessage()).isNotEmpty();
  }

  @Test
  void testCallReturn() {
    var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      "<crs:call_return xmlns:crs=\"http://v8.1c.ru/8.2/crs\">\n" +
      "\t<crs:info>\n" +
      "\t\t<crs:info>\n" +
      "\t\t\t<crs:version>\n" +
      "\t\t\t\t<crs:major value=\"7\"/>\n" +
      "\t\t\t\t<crs:minor value=\"0\"/>\n" +
      "\t\t\t</crs:version>\n" +
      "\t\t\t<crs:depotID value=\"7b676532-75fa-11eb-ff80-0242ac150002\"/>\n" +
      "\t\t\t<crs:rootID value=\"c9dd0f2c-4ed0-484a-baad-56494aa67301\"/>\n" +
      "\t\t\t<crs:mode value=\"80306\"/>\n" +
      "\t\t</crs:info>\n" +
      "\t\t<crs:date value=\"2021-02-23T17:13:43\"/>\n" +
      "\t\t<crs:isExtension value=\"true\"/>\n" +
      "\t</crs:info>\n" +
      "</crs:call_return>";

    var response = (CallReturnResponse) XStreamHelper.readFromString(xml);
    assertThat(response.getCRSInfo().isExtension()).isTrue();
    assertThat(response.getCRSInfo().getDate()).isEqualTo("2021-02-23 17:13:43.0");
    assertThat(response.getCRSInfo().getCRSInfo().getMode()).hasToString("80306");
    assertThat(response.getCRSInfo().getCRSInfo().getDepotID()).hasToString("7b676532-75fa-11eb-ff80-0242ac150002");
    assertThat(response.getCRSInfo().getCRSInfo().getRootID()).hasToString("c9dd0f2c-4ed0-484a-baad-56494aa67301");
  }

  @Test
  void testGetUser() {
    var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call_return xmlns:crs=\"http://v8.1c.ru/8.2/crs\">" +
      "<crs:user>" +
      "<crs:info>" +
      "<crs:id value=\"f02fb7e4-8477-11eb-c986-0242ac150002\"/>" +
      "<crs:name value=\"Администратор\"/>" +
      "<crs:password value=\"06d49632c9dc9bcb62aeaef99612ba6b\"/>" +
      "<crs:rights value=\"65535\"/>" +
      "</crs:info>" +
      "<crs:online value=\"true\"/>" +
      "<crs:removed value=\"false\"/>" +
      "</crs:user>" +
      "</crs:call_return>";

    var response = (CallReturnResponse) XStreamHelper.readFromString(xml);
    assertThat(response.getUser()).isNotNull();
    assertThat(response.getUser().isOnline()).isTrue();
    assertThat(response.getUser().isRemoved()).isFalse();
    assertThat(response.getUser().getInfo().getId()).hasToString("f02fb7e4-8477-11eb-c986-0242ac150002");
    assertThat(response.getUser().getInfo().getName()).hasToString("Администратор");
    assertThat(response.getUser().getInfo().getPassword()).hasToString("06d49632c9dc9bcb62aeaef99612ba6b");
    assertThat(response.getUser().getInfo().getRights()).hasToString("65535");
  }

  @Test
  void testGetUsers() {
    var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      "<crs:call_return\n" +
      "\txmlns:crs=\"http://v8.1c.ru/8.2/crs\">\n" +
      "\t<crs:statMap>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:first value=\"7272e7d5-3aac-48ca-946a-a400719e615c\"/>\n" +
      "\t\t\t<crs:second>\n" +
      "\t\t\t\t<crs:verNum>1</crs:verNum>\n" +
      "\t\t\t\t<crs:revised value=\"false\"/>\n" +
      "\t\t\t\t<crs:revisorID value=\"00000000-0000-0000-0000-000000000000\"/>\n" +
      "\t\t\t\t<crs:reviseDate value=\"2021-03-14T03:47:03\"/>\n" +
      "\t\t\t</crs:second>\n" +
      "\t\t</crs:value>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:first value=\"04b7d69b-e887-4a2d-8989-84d00378ac61\"/>\n" +
      "\t\t\t<crs:second>\n" +
      "\t\t\t\t<crs:verNum>1</crs:verNum>\n" +
      "\t\t\t\t<crs:revised value=\"false\"/>\n" +
      "\t\t\t\t<crs:revisorID value=\"00000000-0000-0000-0000-000000000000\"/>\n" +
      "\t\t\t\t<crs:reviseDate value=\"2021-03-14T03:47:03\"/>\n" +
      "\t\t\t</crs:second>\n" +
      "\t\t</crs:value>\n" +
      "\t</crs:statMap>\n" +
      "\t<crs:bindInfos>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:userID value=\"f02fb7e4-8477-11eb-c986-0242ac150002\"/>\n" +
      "\t\t\t<crs:bindID value=\"3890eef3-a52d-40ed-83c3-ba540e440d5f\"/>\n" +
      "\t\t\t<crs:bindString value=\"Computer=&quot;DESKTOP-S3AOR93&quot;;Config=&quot;C:\\Users\\otymko\\Documents\\InfoBase12&quot;;\"/>\n" +
      "\t\t\t<crs:online value=\"false\"/>\n" +
      "\t\t</crs:value>\n" +
      "\t</crs:bindInfos>\n" +
      "\t<crs:users>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:first value=\"51a81a60-0477-46e5-9f7e-472b01e50f3e\"/>\n" +
      "\t\t\t<crs:second>\n" +
      "\t\t\t\t<crs:info>\n" +
      "\t\t\t\t\t<crs:id value=\"51a81a60-0477-46e5-9f7e-472b01e50f3e\"/>\n" +
      "\t\t\t\t\t<crs:name value=\"user_b208bdc6-4fa3-46ff-ac04-d60ef4f3835e\"/>\n" +
      "\t\t\t\t\t<crs:password value=\"06d49632c9dc9bcb62aeaef99612ba6b\"/>\n" +
      "\t\t\t\t\t<crs:rights value=\"1\"/>\n" +
      "\t\t\t\t</crs:info>\n" +
      "\t\t\t\t<crs:online value=\"false\"/>\n" +
      "\t\t\t\t<crs:removed value=\"false\"/>\n" +
      "\t\t\t</crs:second>\n" +
      "\t\t</crs:value>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:first value=\"f02fb7e4-8477-11eb-c986-0242ac150002\"/>\n" +
      "\t\t\t<crs:second>\n" +
      "\t\t\t\t<crs:info>\n" +
      "\t\t\t\t\t<crs:id value=\"f02fb7e4-8477-11eb-c986-0242ac150002\"/>\n" +
      "\t\t\t\t\t<crs:name value=\"Администратор\"/>\n" +
      "\t\t\t\t\t<crs:password value=\"06d49632c9dc9bcb62aeaef99612ba6b\"/>\n" +
      "\t\t\t\t\t<crs:rights value=\"65535\"/>\n" +
      "\t\t\t\t</crs:info>\n" +
      "\t\t\t\t<crs:online value=\"false\"/>\n" +
      "\t\t\t\t<crs:removed value=\"false\"/>\n" +
      "\t\t\t</crs:second>\n" +
      "\t\t</crs:value>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:first value=\"d344a940-22dd-4cc8-ad22-dc624e4008d5\"/>\n" +
      "\t\t\t<crs:second>\n" +
      "\t\t\t\t<crs:info>\n" +
      "\t\t\t\t\t<crs:id value=\"d344a940-22dd-4cc8-ad22-dc624e4008d5\"/>\n" +
      "\t\t\t\t\t<crs:name value=\"user_27a3d1cd-6d68-41ed-85b2-fc36ec56688a\"/>\n" +
      "\t\t\t\t\t<crs:password value=\"06d49632c9dc9bcb62aeaef99612ba6b\"/>\n" +
      "\t\t\t\t\t<crs:rights value=\"1\"/>\n" +
      "\t\t\t\t</crs:info>\n" +
      "\t\t\t\t<crs:online value=\"false\"/>\n" +
      "\t\t\t\t<crs:removed value=\"true\"/>\n" +
      "\t\t\t</crs:second>\n" +
      "\t\t</crs:value>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:first value=\"e2204610-89fd-4be0-9653-bf93335dc385\"/>\n" +
      "\t\t\t<crs:second>\n" +
      "\t\t\t\t<crs:info>\n" +
      "\t\t\t\t\t<crs:id value=\"e2204610-89fd-4be0-9653-bf93335dc385\"/>\n" +
      "\t\t\t\t\t<crs:name value=\"user_dc16001a-ea86-4d11-ab1d-8ec1f78e262c\"/>\n" +
      "\t\t\t\t\t<crs:password value=\"06d49632c9dc9bcb62aeaef99612ba6b\"/>\n" +
      "\t\t\t\t\t<crs:rights value=\"1\"/>\n" +
      "\t\t\t\t</crs:info>\n" +
      "\t\t\t\t<crs:online value=\"false\"/>\n" +
      "\t\t\t\t<crs:removed value=\"false\"/>\n" +
      "\t\t\t</crs:second>\n" +
      "\t\t</crs:value>\n" +
      "\t\t<crs:value>\n" +
      "\t\t\t<crs:first value=\"11ee7b12-160e-436c-a1e5-d3c4e1fc763c\"/>\n" +
      "\t\t\t<crs:second>\n" +
      "\t\t\t\t<crs:info>\n" +
      "\t\t\t\t\t<crs:id value=\"11ee7b12-160e-436c-a1e5-d3c4e1fc763c\"/>\n" +
      "\t\t\t\t\t<crs:name value=\"user_51f70252-8f9f-4c2e-9419-d6531e789344\"/>\n" +
      "\t\t\t\t\t<crs:password value=\"06d49632c9dc9bcb62aeaef99612ba6b\"/>\n" +
      "\t\t\t\t\t<crs:rights value=\"1\"/>\n" +
      "\t\t\t\t</crs:info>\n" +
      "\t\t\t\t<crs:online value=\"false\"/>\n" +
      "\t\t\t\t<crs:removed value=\"false\"/>\n" +
      "\t\t\t</crs:second>\n" +
      "\t\t</crs:value>\n" +
      "\t</crs:users>\n" +
      "</crs:call_return>";

    var response = (CallReturnResponse) XStreamHelper.readFromString(xml);
    assertThat(response.getUsers().getCRSValues())
      .hasSize(5)
      .anyMatch(crsValue -> crsValue.getId().equals("f02fb7e4-8477-11eb-c986-0242ac150002")
        && crsValue.getUser().getInfo().getId().equals("f02fb7e4-8477-11eb-c986-0242ac150002")
        && crsValue.getUser().getInfo().getName().equals("Администратор")
      )
    ;
  }

}