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
package com.github.otymko.bsl.crs_api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryClientTest {
  private static final String URL = "http://localhost:5000/repo/repo.1ccr";
  private static final String PLATFORM_VERSION = "8.3.12.1855";
  private static final String REPO_USER = "Администратор";

  @Test
  void testConnection() {
    // give
    var password = "1";

    //when
    var client = getClient();
    var connectionEstablished = isConnectionEstablished(client, password);

    // then
    assertThat(connectionEstablished).isTrue();

    // when
    password = "0";
    client = getClient();
    connectionEstablished = isConnectionEstablished(client, password);

    // then
    assertThat(connectionEstablished).isFalse();
  }

  @Test
  void testErrorConnection() {
    // give
    var url = "http://localhost:5001/repo/repo.1ccr";
    var password = "1";

    //when
    var client = getClient(url);
    var connectionEstablished = isConnectionEstablished(client, password);

    // then
    assertThat(connectionEstablished).isFalse();
  }

  @SneakyThrows
  @Test
  void testCreateUser() {
    // give
    var password = "1";
    var user = "user_" + UUID.randomUUID().toString();
    var userPassword = "1";
    var role = "1";

    // when
    var client = getClient();
    client.connect(REPO_USER, password);

    boolean userIsCreated;
    try {
      client.createUser(user, userPassword, role);
      userIsCreated = true;
    } catch (RepositoryClientException exception) {
      userIsCreated = false;
    }

    // then
    assertThat(userIsCreated).isTrue();

    // when
    try {
      client.createUser(user, userPassword, role);
      userIsCreated = true;
    } catch (RepositoryClientException exception) {
      userIsCreated = false;
    }

    // then
    assertThat(userIsCreated).isFalse();
  }

  @SneakyThrows
  @Test
  void testGetUser() {
    // give
    var password = "1";
    var user = REPO_USER + "1";

    // when
    var client = getClient();
    client.connect(REPO_USER, password);

    RepositoryUser repositoryUser;
    try {
      repositoryUser = client.getUser(REPO_USER);
    } catch (RepositoryClientException exception) {
      repositoryUser = null;
    }
    assertThat(repositoryUser).isNotNull();
    assertThat(repositoryUser.getName()).hasToString(REPO_USER);

    try {
      repositoryUser = client.getUser(user);
    } catch (RepositoryClientException exception) {
      repositoryUser = null;
    }
    assertThat(repositoryUser).isNull();
  }

  @SneakyThrows
  @Test
  void testGetUsers() {
    // give
    var password = "1";

    // when
    var client = getClient();
    client.connect(REPO_USER, password);
    var repositoryUsers = client.getUsers();

    // then
    assertThat(repositoryUsers)
      .isNotEmpty()
      .anyMatch(repositoryUser -> repositoryUser.getName().equals(REPO_USER));

  }

  private boolean isConnectionEstablished(RepositoryClient client, String password) {
    boolean connectionEstablished;
    try {
      client.connect(REPO_USER, password);
      connectionEstablished = true;
    } catch (RepositoryClientException exception) {
      connectionEstablished = false;
    }
    return connectionEstablished;
  }

  private RepositoryClient getClient(String url) {
    return new RepositoryClient(url, PLATFORM_VERSION);
  }

  private RepositoryClient getClient() {
    return getClient(URL);
  }

}