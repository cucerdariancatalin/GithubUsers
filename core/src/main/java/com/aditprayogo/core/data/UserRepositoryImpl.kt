package com.aditprayogo.core.data

import com.aditprayogo.core.data.local.db.dao.UserFavoriteDao
import com.aditprayogo.core.data.local.responses.UserDetailResponse
import com.aditprayogo.core.data.local.responses.UserFollowersResponseItem
import com.aditprayogo.core.data.local.responses.UserFollowingResponseItem
import com.aditprayogo.core.data.local.responses.UserSearchResponseItem
import com.aditprayogo.core.data.remote.NetworkService
import com.aditprayogo.core.domain.model.UserFavorite
import com.aditprayogo.core.domain.model.UserSearchItem
import com.aditprayogo.core.domain.repository.UserRepository
import com.aditprayogo.core.utils.DataMapper
import com.aditprayogo.core.utils.state.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val userFavoriteDao: UserFavoriteDao
) : UserRepository {

    /**
     * Remote
     */
    override suspend fun getUserFromApi(username: String): Flow<ResultState<List<UserSearchItem>>> {
        return flow {
            try {
                val response = networkService.getSearchUser(username)
                val userItems = response.userItems
                val dataMaped = response.userItems?.let { listSearchUser ->
                    DataMapper.mapUserSearchResponseToDomain(listSearchUser)
                }
                emit(ResultState.Success(dataMaped))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailUserFromApi(username: String): Flow<ResultState<UserDetailResponse>> {
        return flow {
            try {
                val response = networkService.getDetailUser(username)
                emit(ResultState.Success(response))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollowers(username: String): Flow<ResultState<List<UserFollowersResponseItem>>> {
        return flow {
            try {
                val response = networkService.getFollowerUser(username)
                emit(ResultState.Success(response))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollowing(username: String): Flow<ResultState<List<UserFollowingResponseItem>>> {
        return flow {
            try {
                val response = networkService.getFollowingUser(username)
                emit(ResultState.Success(response))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Local
     */
    override fun fetchAllUserFavorite(): Flow<List<UserFavorite>> {
        return userFavoriteDao.fetchAllUsers().map {
            DataMapper.mapUserFavoriteEntitiesToDomain(it)
        }
    }

    override fun getFavoriteUserByUsername(username: String): Flow<List<UserFavorite>> {
        return userFavoriteDao.getFavByUsername(username).map {
            DataMapper.mapUserFavoriteEntitiesToDomain(it)
        }
    }

    override suspend fun addUserToFavDB(userFavorite: UserFavorite) {
        val data = DataMapper.mapUserFavoriteDomainToEntity(userFavorite)
        return userFavoriteDao.addUserToFavoriteDB(data)
    }

    override suspend fun deleteUserFromFavDB(userFavorite: UserFavorite) {
        val data = DataMapper.mapUserFavoriteDomainToEntity(userFavorite)
        return userFavoriteDao.deleteUserFromFavoriteDB(data)
    }

}