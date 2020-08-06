package com.example.githubusers.di.module

import com.example.githubusers.feature.detail.UserDetailActivity
import com.example.githubusers.feature.favorite.FavoriteUserActivity
import com.example.githubusers.feature.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeUserDetailActivity() : UserDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeFavoriteUserActivity() : FavoriteUserActivity

}