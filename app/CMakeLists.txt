
#
# Copyright (C) The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

cmake_minimum_required(VERSION 3.4.1)

# configure import libs
set(distribution_DIR ${CMAKE_SOURCE_DIR}/../distribution)

add_library(lib_crypto STATIC IMPORTED)
set_target_properties(lib_crypto PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/openssl/lib/${ANDROID_ABI}/libcrypto.a)

add_library(lib_ssl STATIC IMPORTED)
set_target_properties(lib_ssl PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/openssl/lib/${ANDROID_ABI}/libssl.a)

add_library(lib_re STATIC IMPORTED)
set_target_properties(lib_re PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/re/lib/${ANDROID_ABI}/libre.a)

add_library(lib_rem STATIC IMPORTED)
set_target_properties(lib_rem PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/rem/lib/${ANDROID_ABI}/librem.a)

add_library(lib_opus STATIC IMPORTED)
set_target_properties(lib_opus PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/opus/lib/${ANDROID_ABI}/libopus.a)

add_library(lib_baresip STATIC IMPORTED)
set_target_properties(lib_baresip PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/baresip/lib/${ANDROID_ABI}/libbaresip.a)

add_library(baresip SHARED src/main/cpp/baresip.c)

target_include_directories(baresip PRIVATE
                           ${distribution_DIR}/openssl/include
                           ${distribution_DIR}/re/include
                           ${distribution_DIR}/rem/include
                           ${distribution_DIR}/baresip/include)

target_link_libraries(baresip
                      android
                      OpenSLES
                      lib_baresip
                      lib_rem
                      lib_re
                      lib_ssl
                      lib_crypto
                      lib_opus
                      z
                      log)

