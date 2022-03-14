/*
 * Copyright (c) 2012-2021 Snowplow Analytics Ltd. All rights reserved.
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
package com.snowplowanalytics.snowplow.rdbloader.common

import java.time.Instant

import io.circe.Json
import io.circe.literal._
import io.circe.syntax._
import io.circe.parser.parse

import com.snowplowanalytics.iglu.core.{SchemaVer, SelfDescribingData, SchemaKey}

import com.snowplowanalytics.snowplow.rdbloader.common.config.ShredderConfig.Compression
import com.snowplowanalytics.snowplow.rdbloader.common.config.Semver
import com.snowplowanalytics.snowplow.rdbloader.common.LoaderMessage.TypesInfo

import org.specs2.mutable.Specification

class LoaderMessageSpec extends Specification {
  "fromMessage" should {
    "decode ShreddingComplete message from valid JSON string" >> {
      val payload = LoaderMessageSpec.ValidMessageJson.noSpaces
      LoaderMessage.fromString(payload) must beRight(LoaderMessageSpec.ValidMessage)
    }
  }

  "selfDescribingData" should {
    "encode into valid self-describing JSON" >> {
      val result = LoaderMessageSpec.ValidMessage.selfDescribingData(false)
      val expected = SelfDescribingData(
        LoaderMessage.ShreddingCompleteKey,
        LoaderMessageSpec.ValidMessageJson.hcursor.downField("data").focus.getOrElse(Json.Null)
      )
      result must beEqualTo(expected)
    }
  }

  "manifestType encoder" should {
    "drop null values in output json" >> {
      val manifestType = LoaderMessage.ManifestType(
        LoaderMessage.ShreddingCompleteKey,
        "TSV",
        None
      )
      val jsonStr = manifestType.asJson.noSpaces
      jsonStr.contains("transformation") must beFalse
      val decoded = parse(jsonStr).flatMap(_.as[LoaderMessage.ManifestType])
      decoded must beRight(manifestType)
    }
  }
}

object LoaderMessageSpec {
  val ValidMessageJson = json"""{
    "schema": "iglu:com.snowplowanalytics.snowplow.storage.rdbloader/shredding_complete/jsonschema/2-0-0",
    "data": {
      "base" : "s3://bucket/folder/",
      "typesInfo": {
        "transformation": "SHREDDED",
        "types": [
          {
            "schemaKey" : "iglu:com.acme/event-a/jsonschema/1-0-0",
            "format" : "TSV",
            "snowplowEntity": "SELF_DESCRIBING_EVENT"
          }
        ]
      },
      "timestamps" : {
        "jobStarted" : "2020-09-17T11:32:21.145Z",
        "jobCompleted" : "2020-09-17T11:32:21.145Z",
        "min" : null,
        "max" : null
      },
      "compression": "GZIP",
      "processor": {
        "artifact" : "test-shredder",
        "version" : "1.1.2"
      },
      "count": null
    }
  }"""

  val ValidMessage: LoaderMessage = LoaderMessage.ShreddingComplete(
    S3.Folder.coerce("s3://bucket/folder/"),
    TypesInfo.Shredded(
      List(
        TypesInfo.Shredded.Type(SchemaKey("com.acme", "event-a", "jsonschema", SchemaVer.Full(1, 0, 0)), TypesInfo.Shredded.ShreddedFormat.TSV, LoaderMessage.SnowplowEntity.SelfDescribingEvent)
      )
    ),
    LoaderMessage.Timestamps(
      Instant.ofEpochMilli(1600342341145L),
      Instant.ofEpochMilli(1600342341145L),
      None,
      None
    ),
    Compression.Gzip,
    LoaderMessage.Processor("test-shredder", Semver(1, 1, 2)),
    None
  )
}
