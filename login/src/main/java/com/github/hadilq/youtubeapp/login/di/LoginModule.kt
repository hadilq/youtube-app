/**
 * Copyright 2020 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.hadilq.youtubeapp.login.di

import com.github.hadilq.youtubeapp.core.di.CoreModule
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.di.LoginModuleSyntax
import com.github.hadilq.youtubeapp.presentation.di.PresentationModule
import com.github.hadilq.youtubeapp.presentation.login.LoginViewModelFactory

interface LoginModule : DomainModule, CoreModule, LoginModuleSyntax {

  val loginViewModelFactory: LoginViewModelFactory
}

fun LoginModuleSyntax.fix() = this as LoginModule

fun LoginModule.fixPresentation() = this as PresentationModule
