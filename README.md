# HttpMockResponseInterceptor

A Mock response interceptor lib, which can mock response for UI test, Instrumentation test and other
test as well, directly from retrofit api class using simple annotation.

Using HttpMockResponseInterceptor, you can fetch data from creating local json files using retrofit
Api class by simply using one additional annotation.

## Dependencies

Add the Jitpack source to project or settings.gradle:

```kotlin
  ...
repositories {
    maven { url 'https://jitpack.io' }
}
```

// or in settings.gradle

```kotlin
pluginManagement {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
dependencyResolutionManagement {

    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the following dependency to your build.gradle(module) file:

```kotlin
  dependencies {
    ...
    implementation 'com.github.devggaurav:HttpMockResponseInterceptor:v1.0.2'
}
```

## Init

```kotlin
HttpMockResponseInterceptor.Builder(context.assets).build()

// or

HttpMockResponseInterceptor.Builder(context.assets).isMockEnabled {
    true // false if you want to get data from server or pass your build variant e.g BuildConfig.DEBUG
}.build()

// or using DI

@Provides
fun provideMockingInterceptor(
    @ApplicationContext context: Context
): HttpMockResponseInterceptor {
    return HttpMockResponseInterceptor.Builder(context.assets).isMockEnabled {
        true
    }.build()
}


```

## Usage

Define your endpoint and just add @MockSuccessResponse annotation for getting data from local json
filename provided

```kotlin
@GET(ApiConstants.END_POINTS)
@MockSuccessResponse(fileName = "characters_response.json")
suspend fun getCharacter(): List<Character>
```

Create Mock Json File in assets
Create response you are expecting from api to test like in characters_response.json

```json
{
  "dummyjson": "Dummy value"
}
```

## Updates

Working on handling and passing multiple files to handle success and error response for testcases.

