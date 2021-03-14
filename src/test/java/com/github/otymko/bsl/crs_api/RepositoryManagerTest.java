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

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryManagerTest {
  private static final String URL = "http://localhost:5000/repo/repo.1ccr";
  private static final String PLATFORM_VERSION = "8.3.12.1855";

  @Test
  void testCreateDepot() {
    var repositoryName = "repo" + UUID.randomUUID().toString();
    boolean success;
    try {
      RepositoryManager.createDepot(URL, repositoryName, PLATFORM_VERSION, "Администратор", "1");
      success = true;
    } catch (RepositoryClientException exception) {
      success = false;
    }
    assertThat(success).isTrue();
  }

}