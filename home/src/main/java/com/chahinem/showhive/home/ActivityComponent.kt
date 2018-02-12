package com.chahinem.showhive.home

import com.chahinem.showhive.base.di.ShowHiveComponent
import com.chahinem.showhive.qualifiers.PerActivity
import dagger.Component

@PerActivity
@Component(
    modules = [ActivityModule::class],
    dependencies = [ShowHiveComponent::class]
)
interface ActivityComponent {

  fun inject(activity: HomeActivity)
//  fun inject(viewModel: FilterViewModel)

  @Component.Builder
  interface Builder {
    fun appComponent(appComponent: ShowHiveComponent): Builder
    fun activityModule(module: ActivityModule): Builder
    fun build(): ActivityComponent
  }
}