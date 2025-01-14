package com.snowplowanalytics.snowplow.rdbloader.common.transformation.parquet

import com.snowplowanalytics.iglu.schemaddl.parquet.{Field, Type}
import com.snowplowanalytics.iglu.schemaddl.parquet.Type.DecimalPrecision
import com.snowplowanalytics.iglu.schemaddl.parquet.Type.Nullability.{Nullable, Required}
import com.snowplowanalytics.snowplow.rdbloader.common.transformation.parquet.fields.AtomicFields

object AtomicFieldsProvider {
  private val customDecimal = Type.Decimal(DecimalPrecision.Digits18, 2)

  val static = AtomicFields(List(
    Field("app_id",                   Type.String,    Nullable),
    Field("platform",                 Type.String,    Nullable),
    Field("etl_tstamp",               Type.Timestamp, Nullable),
    Field("collector_tstamp",         Type.Timestamp, Required),
    Field("dvce_created_tstamp",      Type.Timestamp, Nullable),
    Field("event",                    Type.String,    Nullable),
    Field("event_id",                 Type.String,    Required),
    Field("txn_id",                   Type.Integer,   Nullable),
    Field("name_tracker",             Type.String,    Nullable),
    Field("v_tracker",                Type.String,    Nullable),
    Field("v_collector",              Type.String,    Required),
    Field("v_etl",                    Type.String,    Required),
    Field("user_id",                  Type.String,    Nullable),
    Field("user_ipaddress",           Type.String,    Nullable),
    Field("user_fingerprint",         Type.String,    Nullable),
    Field("domain_userid",            Type.String,    Nullable),
    Field("domain_sessionidx",        Type.Integer,   Nullable),
    Field("network_userid",           Type.String,    Nullable),
    Field("geo_country",              Type.String,    Nullable),
    Field("geo_region",               Type.String,    Nullable),
    Field("geo_city",                 Type.String,    Nullable),
    Field("geo_zipcode",              Type.String,    Nullable),
    Field("geo_latitude",             Type.Double,    Nullable),
    Field("geo_longitude",            Type.Double,    Nullable),
    Field("geo_region_name",          Type.String,    Nullable),
    Field("ip_isp",                   Type.String,    Nullable),
    Field("ip_organization",          Type.String,    Nullable),
    Field("ip_domain",                Type.String,    Nullable),
    Field("ip_netspeed",              Type.String,    Nullable),
    Field("page_url",                 Type.String,    Nullable),
    Field("page_title",               Type.String,    Nullable),
    Field("page_referrer",            Type.String,    Nullable),
    Field("page_urlscheme",           Type.String,    Nullable),
    Field("page_urlhost",             Type.String,    Nullable),
    Field("page_urlport",             Type.Integer,   Nullable),
    Field("page_urlpath",             Type.String,    Nullable),
    Field("page_urlquery",            Type.String,    Nullable),
    Field("page_urlfragment",         Type.String,    Nullable),
    Field("refr_urlscheme",           Type.String,    Nullable),
    Field("refr_urlhost",             Type.String,    Nullable),
    Field("refr_urlport",             Type.Integer,   Nullable),
    Field("refr_urlpath",             Type.String,    Nullable),
    Field("refr_urlquery",            Type.String,    Nullable),
    Field("refr_urlfragment",         Type.String,    Nullable),
    Field("refr_medium",              Type.String,    Nullable),
    Field("refr_source",              Type.String,    Nullable),
    Field("refr_term",                Type.String,    Nullable),
    Field("mkt_medium",               Type.String,    Nullable),
    Field("mkt_source",               Type.String,    Nullable),
    Field("mkt_term",                 Type.String,    Nullable),
    Field("mkt_content",              Type.String,    Nullable),
    Field("mkt_campaign",             Type.String,    Nullable),
    Field("se_category",              Type.String,    Nullable),
    Field("se_action",                Type.String,    Nullable),
    Field("se_label",                 Type.String,    Nullable),
    Field("se_property",              Type.String,    Nullable),
    Field("se_value",                 Type.Double,    Nullable),
    Field("tr_orderid",               Type.String,    Nullable),
    Field("tr_affiliation",           Type.String,    Nullable),
    Field("tr_total",                 customDecimal,  Nullable),
    Field("tr_tax",                   customDecimal,  Nullable),
    Field("tr_shipping",              customDecimal,  Nullable),
    Field("tr_city",                  Type.String,    Nullable),
    Field("tr_state",                 Type.String,    Nullable),
    Field("tr_country",               Type.String,    Nullable),
    Field("ti_orderid",               Type.String,    Nullable),
    Field("ti_sku",                   Type.String,    Nullable),
    Field("ti_name",                  Type.String,    Nullable),
    Field("ti_category",              Type.String,    Nullable),
    Field("ti_price",                 customDecimal,  Nullable),
    Field("ti_quantity",              Type.Integer,   Nullable),
    Field("pp_xoffset_min",           Type.Integer,   Nullable),
    Field("pp_xoffset_max",           Type.Integer,   Nullable),
    Field("pp_yoffset_min",           Type.Integer,   Nullable),
    Field("pp_yoffset_max",           Type.Integer,   Nullable),
    Field("useragent",                Type.String,    Nullable),
    Field("br_name",                  Type.String,    Nullable),
    Field("br_family",                Type.String,    Nullable),
    Field("br_version",               Type.String,    Nullable),
    Field("br_type",                  Type.String,    Nullable),
    Field("br_renderengine",          Type.String,    Nullable),
    Field("br_lang",                  Type.String,    Nullable),
    Field("br_features_pdf",          Type.Boolean,   Nullable),
    Field("br_features_flash",        Type.Boolean,   Nullable),
    Field("br_features_java",         Type.Boolean,   Nullable),
    Field("br_features_director",     Type.Boolean,   Nullable),
    Field("br_features_quicktime",    Type.Boolean,   Nullable),
    Field("br_features_realplayer",   Type.Boolean,   Nullable),
    Field("br_features_windowsmedia", Type.Boolean,   Nullable),
    Field("br_features_gears",        Type.Boolean,   Nullable),
    Field("br_features_silverlight",  Type.Boolean,   Nullable),
    Field("br_cookies",               Type.Boolean,   Nullable),
    Field("br_colordepth",            Type.String,    Nullable),
    Field("br_viewwidth",             Type.Integer,   Nullable),
    Field("br_viewheight",            Type.Integer,   Nullable),
    Field("os_name",                  Type.String,    Nullable),
    Field("os_family",                Type.String,    Nullable),
    Field("os_manufacturer",          Type.String,    Nullable),
    Field("os_timezone",              Type.String,    Nullable),
    Field("dvce_type",                Type.String,    Nullable),
    Field("dvce_ismobile",            Type.Boolean,   Nullable),
    Field("dvce_screenwidth",         Type.Integer,   Nullable),
    Field("dvce_screenheight",        Type.Integer,   Nullable),
    Field("doc_charset",              Type.String,    Nullable),
    Field("doc_width",                Type.Integer,   Nullable),
    Field("doc_height",               Type.Integer,   Nullable),
    Field("tr_currency",              Type.String,    Nullable),
    Field("tr_total_base",            customDecimal,  Nullable),
    Field("tr_tax_base",              customDecimal,  Nullable),
    Field("tr_shipping_base",         customDecimal,  Nullable),
    Field("ti_currency",              Type.String,    Nullable),
    Field("ti_price_base",            customDecimal,  Nullable),
    Field("base_currency",            Type.String,    Nullable),
    Field("geo_timezone",             Type.String,    Nullable),
    Field("mkt_clickid",              Type.String,    Nullable),
    Field("mkt_network",              Type.String,    Nullable),
    Field("etl_tags",                 Type.String,    Nullable),
    Field("dvce_sent_tstamp",         Type.Timestamp, Nullable),
    Field("refr_domain_userid",       Type.String,    Nullable),
    Field("refr_dvce_tstamp",         Type.Timestamp, Nullable),
    Field("domain_sessionid",         Type.String,    Nullable),
    Field("derived_tstamp",           Type.Timestamp, Nullable),
    Field("event_vendor",             Type.String,    Nullable),
    Field("event_name",               Type.String,    Nullable),
    Field("event_format",             Type.String,    Nullable),
    Field("event_version",            Type.String,    Nullable),
    Field("event_fingerprint",        Type.String,    Nullable),
    Field("true_tstamp",              Type.Timestamp, Nullable)
  ))

}
