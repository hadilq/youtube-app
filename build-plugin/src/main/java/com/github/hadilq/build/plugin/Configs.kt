@file:Suppress("UnstableApiUsage")

package com.github.hadilq.build.plugin

import com.android.build.api.dsl.*
import com.android.build.api.variant.Variant
import com.android.build.api.variant.VariantProperties
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun Project.setupAndroidLibrary() {

  extensions.getByType<LibraryExtension>().run {
    compileSdkVersion(VERSION_COMPILE_SDK)
    defaultConfig {
      minSdkVersion(VERSION_MIN_SDK)
      targetSdkVersion(VERSION_TARGET_SDK)
    }

    setupJavaTarget()
  }

  addAndroidBasics()
  addJUnit()
}

fun <AndroidSourceSetT : AndroidSourceSet,
    BuildFeaturesT : BuildFeatures,
    BuildTypeT : BuildType,
    DefaultConfigT : DefaultConfig,
    ProductFlavorT : ProductFlavor,
    SigningConfigT : SigningConfig,
    VariantT : Variant<VariantPropertiesT>,
    VariantPropertiesT : VariantProperties>
    CommonExtension<
        AndroidSourceSetT,
        BuildFeaturesT,
        BuildTypeT,
        DefaultConfigT,
        ProductFlavorT,
        SigningConfigT,
        VariantT,
        VariantPropertiesT
        >.setupJavaTarget() {

  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }

  (this as? ExtensionAware)?.extensions?.getByType<KotlinJvmOptions>()?.run {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}

fun Project.addAndroidBasics(configurationName: String = "implementation") {
  dependencies.add(configurationName, ANDROIDX_CORE)
  dependencies.add(configurationName, ANDROIDX_APPCOMPAT)
  dependencies.add(configurationName, ANDROID_MATERIAL)
}

fun Project.addJUnit() {
  dependencies.add("testImplementation", JUPITER_API)
  dependencies.add("testRuntimeOnly", JUPITER_ENGINE)
  tasks.withType<Test> {
    useJUnitPlatform()
  }
}
