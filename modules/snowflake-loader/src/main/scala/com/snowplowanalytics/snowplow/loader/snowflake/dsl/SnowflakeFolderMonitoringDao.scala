package com.snowplowanalytics.snowplow.loader.snowflake.dsl

import cats.MonadThrow
import cats.implicits._

import com.snowplowanalytics.snowplow.rdbloader.common.S3.Folder
import com.snowplowanalytics.snowplow.rdbloader.algebras.db.FolderMonitoringDao
import com.snowplowanalytics.snowplow.rdbloader.LoaderError
import com.snowplowanalytics.snowplow.loader.snowflake.db.{SfDao, Statement, SnowflakeManifest}
import com.snowplowanalytics.snowplow.loader.snowflake.config.SnowflakeTarget

class SnowflakeFolderMonitoringDao[C[_]: SfDao: MonadThrow](target: SnowflakeTarget) extends FolderMonitoringDao[C] {
  import SnowflakeFolderMonitoringDao._

  override def dropAlertingTempTable: C[Unit] =
    SfDao[C].executeUpdate(Statement.DropAlertingTempTable(target.schema, AlertingTempTableName)).as(())

  override def createAlertingTempTable: C[Unit] =
    SfDao[C].executeUpdate(Statement.CreateAlertingTempTable(target.schema, AlertingTempTableName)).as(())

  override def foldersCopy(source: Folder): C[Unit] = {
    val runId = source.split("/").last
    val loadPath = s"shredded/$runId"
    for {
      s <- folderMonitoringStage
      _ <- SfDao[C].executeUpdate(Statement.FoldersCopy(target.schema, AlertingTempTableName, s, loadPath))
    } yield ()
  }

  override def foldersMinusManifest: C[List[Folder]] =
    SfDao[C].executeQueryList(Statement.FoldersMinusManifest(target.schema, AlertingTempTableName, SnowflakeManifest.ManifestTable))

  def folderMonitoringStage: C[String] =
    target.folderMonitoringStage match {
      case None => MonadThrow[C].raiseError(LoaderError.StorageTargetError("Stage for folder monitoring isn't provided. Copy for folder monitoring couldn't be performed."))
      case Some(stage) => MonadThrow[C].pure(stage)
    }
}

object SnowflakeFolderMonitoringDao {
  val AlertingTempTableName = "rdb_folder_monitoring"
}