package com.example.android.devbyteviewer

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException

/**
 * Created by Clarence E Moore on 2023-02-17.
 *
 * Description:
 *
 *
 */
class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
        CoroutineWorker(appContext, params) {
            companion object {
                const val WORK_NAME = "RefreshDataWorker"
            }

    override suspend fun doWork(): Result {

        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        return try {
            repository.refreshVideos()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
