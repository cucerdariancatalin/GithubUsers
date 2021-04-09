package com.example.githubusers.data.remote

import com.example.githubusers.BuildConfig
import com.example.githubusers.data.local.responses.SearchUserResponse
import com.example.githubusers.data.local.responses.UserDetailResponse
import com.example.githubusers.data.local.responses.UserFollowersResponse
import com.example.githubusers.data.local.responses.UserFollowingResponse
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    /**
     * Endpoints search User
     */
    @GET("search/users?")
    suspend fun getSearchUser(
        @Query("q") q : String
    ) : Response<SearchUserResponse>

    /**
     * Endpoints Detail User
     */
    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : Response<UserDetailResponse>

    /**
     * Endpoints Followers
     */
    @GET("users/{username}/followers")
    suspend fun getFollowerUser(
        @Path("username") username: String
    ) : Response<UserFollowersResponse>

    /**
     * Endpoints Following
     */
    @GET("users/{username}/following")
    suspend fun getFollowingUser(
        @Path("username") username: String
    ) : Response<UserFollowingResponse>


}