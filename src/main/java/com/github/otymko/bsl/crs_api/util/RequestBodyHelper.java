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

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.stream.Collectors;

// FIXME: переделать на правильное создание xml сообщений к релизу 0.3.0
@UtilityClass
public class RequestBodyHelper {
  public final String TEMPLATE_CREATE_DEPOT = getTemplateCreateDepot();
  private final String TEMPLATE_ROOT_ID = "c9dd0f2c-4ed0-484a-baad-56494aa67301";

  public String createDevDepot(String repository, String platformVersion, String user, String hash, String template) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"DevDepotAdmin_createDevDepot\" version=\"" + platformVersion + "\">" +
      "<crs:params>" +
      "<crs:alias value=\"" + repository + "\"/>" +
      "<crs:rootID value=\"" + TEMPLATE_ROOT_ID + "\"/>" +
      "<crs:adminName value=\"" + user + "\"/>" +
      "<crs:adminPassword value=\"" + hash + "\"/>" +
      "<crs:code value=\"\"/>" +
      "<crs:features/>" +
      "<crs:snapshots>" +
      "<crs:data>" + template + "</crs:data>" +
      "</crs:snapshots>" +
      "<crs:hashedVersionID value=\"false\"/>" +
      "</crs:params>" +
      "</crs:call>";
  }

  public String depotInfo(String repository, String platformVersion, String user, String passwordHash) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"DevDepot_depotInfo\" " +
      "version=\"" + platformVersion + "\"> " +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\"/> " +
      "<crs:params/> " +
      "</crs:call>";
  }

  public String addUser(String repository, String platformVersion, String user, String passwordHash,
                        String userName, String hash, String role) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"UserManager_addUser\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\" />" +
      "<crs:params>" +
      "<crs:user>" +
      "<crs:id value=\"" + UUID.randomUUID().toString() + "\" />" +
      "<crs:name value=\"" + userName + "\" />" +
      "<crs:password value=\"" + hash + "\" />" +
      "<crs:rights value=\"" + role + "\" />" +
      "</crs:user>" +
      "</crs:params>" +
      "</crs:call>";
  }

  public String depotUserByName(String repository, String platformVersion, String user, String passwordHash,
                                String userName) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"UserManager_depotUserByName\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\" />" +
      "<crs:params>" +
      "<crs:name value=\"" + userName + "\" />" +
      "</crs:params>" +
      "</crs:call>";
  }

  public String removeUser(String repository, String platformVersion, String user, String passwordHash, String userId) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"UserManager_removeUser\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\"/>" +
      "<crs:params>" +
      "<crs:id value=\"" + userId + "\"/>" +
      "</crs:params>" +
      "</crs:call>";
  }

  public String resurrectUser(String repository, String platformVersion, String user, String passwordHash, String userId) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"UserManager_resurrectUser\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\"/>" +
      "<crs:params>" +
      "<crs:id value=\"" + userId + "\"/>" +
      "</crs:params>\n" +
      "</crs:call>";
  }

  public String devObjectsStatistic(String repository, String platformVersion, String user, String passwordHash) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"DevDepot_devObjectsStatistic\" version=\"" + platformVersion + "\">" +
      "<crs:auth user=\"" + user + "\" password=\"" + passwordHash + "\" />" +
      "<crs:bind bindID=\"" + UUID.randomUUID().toString() + "\"/>" +
      "<crs:params>" +
      "<crs:objRefs/>" +
      "<crs:removed value=\"true\"/>" +
      "</crs:params>" +
      "</crs:call>";
  }

  private String getTemplateCreateDepot() {
    var inputStream = RequestBodyHelper.class.getResourceAsStream("/createDepot.txt");
    var reader = new InputStreamReader(inputStream);
    return new BufferedReader(reader).lines().parallel().collect(Collectors.joining("\n"));
  }

  public String openDevDepot(String repository, String platformVersion) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<crs:call xmlns:crs=\"http://v8.1c.ru/8.2/crs\" alias=\"" + repository + "\" name=\"DevDepotAdmin_openDevDepot\" version=\"" + platformVersion + "\">" +
      "<crs:params>" +
      "<crs:alias value=\"" + repository + "\"/>" +
      "<crs:convert value=\"false\"/>" +
      "</crs:params>" +
      "</crs:call>";
  }
}
