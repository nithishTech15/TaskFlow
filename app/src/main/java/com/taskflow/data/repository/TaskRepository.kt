package com.taskflow.data.repository

import com.taskflow.data.local.TaskDao
import com.taskflow.data.local.TaskEntity
import com.taskflow.data.remote.TaskApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val apiService: TaskApiService
) {
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    suspend fun refreshTasks() {
        try {
            val remoteTasks = apiService.getTasks()
            val entities = remoteTasks.map { dto ->
                TaskEntity(
                    title = dto.title,
                    description = dto.description ?: "",
                    isCompleted = dto.completed ?: false
                )
            }
            for (entity in entities) {
                taskDao.insertTask(entity)
            }
        } catch (e: Exception) {
            // Handle error or offline fallback
        }
    }
}
