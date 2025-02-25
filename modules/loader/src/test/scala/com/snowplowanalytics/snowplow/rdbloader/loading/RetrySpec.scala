/*
 * Copyright (c) 2012-2022 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.snowplow.rdbloader.loading


import org.specs2.mutable.Specification
import java.sql.SQLException

class RetrySpec extends Specification {
  "isWorth" should {
    "return false for IllegalStateException" in {
      val input = new IllegalStateException("boom")
      Retry.isWorth(input) should beFalse
    }

    "return true for arbitrary SQLException" in {
      val input = new SQLException("arbitrary")
      Retry.isWorth(input) should beTrue
    }
  }
}
