package co.anitrend.support.markdown.buildSrc

import co.anitrend.support.markdown.buildSrc.common.Versions

object Libraries {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
	
	const val betterLinkMovement = "me.saket:better-link-movement-method:${Versions.betterLinkMovement}"
	
    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    const val elements = "com.otaliastudios:elements:${Versions.elements}"


    object Repositories {
        const val jitPack = "https://www.jitpack.io"
        const val markdown = "https://dl.bintray.com/jetbrains/markdown"
        const val dependencyUpdates = "https://dl.bintray.com/pdvrieze/maven"
    }

    object Android {

        object Tools {
            private const val version = "7.0.2"
            const val buildGradle = "com.android.tools.build:gradle:$version"
        }
    }

    object AndroidX {

        object Activity {
            private const val version = "1.2.0"
            const val activity = "androidx.activity:activity:$version"
            const val activityKtx = "androidx.activity:activity-ktx:$version"
        }

        object Collection {
            private const val version = "1.1.0"
            const val collection = "androidx.collection:collection:$version"
            const val collectionKtx = "androidx.collection:collection-ktx:$version"
        }

        object Core {
            private const val version = "1.5.0-beta02"
            const val core = "androidx.core:core:$version"
            const val coreKtx = "androidx.core:core-ktx:$version"
        }

        object ConstraintLayout {
            private const val version = "2.0.0"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:$version"
            const val constraintLayoutSolver = "androidx.constraintlayout:constraintlayout-solver:$version"
        }

        object Fragment {
            private const val version = "1.3.2"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
            const val test = "androidx.fragment:fragment-ktx:fragment-testing$version"
        }

        object Lifecycle {
            private const val version = "2.3.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val runTimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveDataCoreKtx = "androidx.lifecycle:lifecycle-livedata-core-ktx:$version"
        }

        object Paging {
            private const val version = "3.0.0-beta03"
            const val common = "androidx.paging:paging-common-ktx:$version"
            const val runtime = "androidx.paging:paging-runtime:$version"
            const val runtimeKtx = "androidx.paging:paging-runtime-ktx:$version"
        }

        object Preference {
            private const val version = "1.1.0"
            const val preference = "androidx.preference:preference:$version"
            const val preferenceKtx = "androidx.preference:preference-ktx:$version"
        }

        object Recycler {
            private const val version = "1.2.0-beta02"
            const val recyclerView = "androidx.recyclerview:recyclerview:$version"
            const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:$version"
        }

        object SwipeRefresh {
            private const val version = "1.1.0-rc01"
            const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:$version"
        }

        object Test {
            private const val version = "1.3.0"
            const val core = "androidx.test:core:$version"
            const val coreKtx = "androidx.test:core-ktx:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Espresso {
                private const val version = "3.3.0"
                const val core = "androidx.test.espresso:espresso-core:$version"
            }

            object Extension {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit:$version"
                const val junitKtx = "androidx.test.ext:junit-ktx:$version"
            }
        }
    }

    object Apollo {
        private const val version = "2.5.5"
        const val runtime = "com.apollographql.apollo:apollo-runtime:$version"
        const val android = "com.apollographql.apollo:apollo-android-support:$version"
        const val coroutines = "com.apollographql.apollo:apollo-coroutines-support:$version"

        object Gradle {
            const val plugin = "com.apollographql.apollo:apollo-gradle-plugin:$version"
        }
    }

    object CashApp {
        object Turbine {
            private const val version = "0.4.1"
            const val turbine = "app.cash.turbine:turbine:$version"
        }
    }

    object Coil {
        private const val version = "1.1.1"
        const val coil = "io.coil-kt:coil:$version"
        const val base = "io.coil-kt:coil-base:$version"
        const val gif = "io.coil-kt:coil-gif:$version"
        const val svg = "io.coil-kt:coil-svg:$version"
        const val video = "io.coil-kt:coil-video:$version"
    }

    object Google {

        object Material {
            private const val version = "1.3.0"
            const val material = "com.google.android.material:material:$version"
        }
    }

    object JetBrains {

        object Dokka {
            private const val version = "1.4.30"
            const val gradlePlugin = "org.jetbrains.dokka:dokka-gradle-plugin:$version"
        }

        object Markdown {
            private const val version = "0.2.1"
            const val markdown = "org.jetbrains:markdown:$version"
        }

        object Kotlin {
            private const val version = "1.4.32"
            const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
            const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"

            object Gradle {
                const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
                const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$version"
            }

            object Android {
                const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
            }
        }

        object KotlinX {
            object Coroutines {
                private const val version = "1.3.8"
                const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
                const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
                const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
            }

            object Serialization {
                private const val version = "0.20.0"
                const val runtime = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$version"
            }
        }
    }

    object Koin {
        private const val version = "2.2.1"
        const val core = "org.koin:koin-core:$version"
        const val extension = "org.koin:koin-core-ext:$version"
        const val test = "org.koin:koin-test:$version"

        object AndroidX {
            const val scope = "org.koin:koin-androidx-scope:$version"
            const val fragment = "org.koin:koin-androidx-fragment:$version"
            const val viewmodel = "org.koin:koin-androidx-viewmodel:$version"
        }

        object Gradle {
            const val plugin = "org.koin:koin-gradle-plugin:$version"
        }
    }

    object Markwon {
        private const val version = "4.6.2"
        const val core = "io.noties.markwon:core:$version"
        const val editor = "io.noties.markwon:editor:$version"
        const val html = "io.noties.markwon:html:$version"
        const val image = "io.noties.markwon:image:$version"
        const val glide = "io.noties.markwon:image-glide:$version"
        const val coil = "io.noties.markwon:image-coil:$version"
        const val parser = "io.noties.markwon:inline-parser:$version"
        const val linkify = "io.noties.markwon:linkify:$version"
        const val simpleExt = "io.noties.markwon:simple-ext:$version"
        const val syntaxHighlight = "io.noties.markwon:syntax-highlight:$version"

        object Extension {
            const val taskList = "io.noties.markwon:ext-tasklist:$version"
            const val strikeThrough = "io.noties.markwon:ext-strikethrough:$version"
            const val tables = "io.noties.markwon:ext-tables:$version"
            const val latex = "io.noties.markwon:ext-latex:$version"
        }
    }
}
