package it.lorisdaniel.jetcosttest.dependency

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.lorisdaniel.jetcosttest.R
import it.lorisdaniel.jetcosttest.api.GoogleSearch
import it.lorisdaniel.jetcosttest.database.AppDatabase
import it.lorisdaniel.jetcosttest.repository.Repository
import it.lorisdaniel.jetcosttest.ui.viewmodel.MainViewModel
import it.lorisdaniel.jetcosttest.utils.LiveDataCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(androidApplication().getString(R.string.search_base_url))
            .client(get())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(GoogleSearch::class.java)
    }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return@single interceptor
    }

    single {
        val cacheSize = 1024 * 1024 * 10
        val okHttpBuilder = OkHttpClient().newBuilder()
        okHttpBuilder.cache(
            Cache(androidApplication().cacheDir, cacheSize.toLong())
        )
        okHttpBuilder.addInterceptor(get<HttpLoggingInterceptor>())
        okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(20, TimeUnit.SECONDS)
        okHttpBuilder.build()
    }

}

val repositoryModule = module {
    single { Repository(get(), get()) }
}

val databaseModule = module {
    single { AppDatabase.getAppDataBase(androidApplication()) }
    single { get<AppDatabase>().itemDao() }
    single { get<AppDatabase>().imageDao() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}