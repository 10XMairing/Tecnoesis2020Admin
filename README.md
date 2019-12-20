### Android Application for Tecnoesis 2020


## The Design

There will be 5 fragments for the app - `Home`, `Events`,`Notification`, `Schedule`,`About`. 




|name|description|
|--|--|
|`Home` | This will highlight the keynote speakers, headliners and sponsers . It will have a recycler view with a cardview . Card will contain the poster for that event and a small description at the bottom . |
|`Events`|It will have a staggered recycler view . This recyclerview will have different modules for tecnoesis, eg - NITS HACKS, Robotron, Spark etc . On clicking this cards it will go to another screen details the details about that modules. Eg - For the Robotron module, if the user clicks it it will go to an screen and show the different events under this module. On clicking those events it will show details about that particular event, photo, description, prize money. |

![enter image description here](https://lh3.googleusercontent.com/2Tcf-YxwLnS__t40_Z__mNF6MBHeBv-YM0G2qBL_TSEWnOSG6I2H7t08SG5Jg5Lle86lYAjVLvIG)

*The events section of the each module may be displayed as a stagerred list.*

|||
|--|--|
|`Notifications`  |One  of the key features of the app . It will have a recyclerview with cards. A card will contain name of the sender and the message body . |
|`Schedule` |It will have a map , map will contain the location for different events and it's timing. |
|`About`  |It will have a recycler view, containing two different categories -Sponsers & Teams . For Sponsers part a brief description of that sponser, link to their website or social handle can be provided . For the teams section - It will contain different teams, on clicking them it will drop down and show the members and leaders of the teams.|



Designers : You are free to decide how the elements will look in the app.


**required libraries :** 

1: butterknife

2: Android Architecture Components -- ViewModel and LiveData

3: Timber : for logging

4: Retrofit and GSON : for Api Requests

5: Material Design Components MDC


## Naming Patterns

**layouts**
`type_name`




|||
|--|--|
|MainActivity|activity_main|
|HomeFragment|fragment_home|
|for listLayouts in recyclers etc|item_user|

**id naming**

    type_context_viewType_purpose

|desc|id|
|-|-|
|TextView in MainActivty|act_main_tv_headline|
|Button in MainActivty|act_main_btn_submit|
|TextView in HomeFragment|frag_home_tv_headline|




## Project Structure



    |-- tenx
        |-- Config.java
        |-- data
        |   |-- AppDataManager.java
        |   |-- AppDataManagerHelper.java
        |   |-- models
        |   |   |-- EventResponse.java
        |   |-- rest
        |       |-- events
        |           |-- AppEventHelper.java
        |           |-- EventsApiService.java
        |-- logging
        |   |-- ReleaseTree.java
        |-- modules
        |   |-- RetrofitProvider.java
        |-- ui
            |-- MyApplication.java
            |-- main
            |   |-- MainActivity.java
            |   |-- MainViewModel.java
            |   |-- about
            |   |   |-- AboutFragment.java
            |   |   |-- AboutViewModel.java
            |   |-- events
            |   |   |-- EventsFragment.java
            |   |   |-- EventsViewModel.java
            |   |-- home
            |   |   |-- HomeFragment.java
            |   |   |-- HomeViewModel.java
            |   |   |-- HomeViewModelHelper.java
            |   |-- schedule
            |       |-- ScheduleFragment.java
            |       |-- ScheduleViewModel.java
            |-- splash
                |-- SplashActivity.java



| directory/files |purpose  |
|--|--|
|  `data`| Contains Data Services , REST calls , Firebase, Shared Prefs etc  |
|`AppDataManager`|Encapsulates all data sources into a single entity by combining the interfaces and implementing them|
|`modules`|provides service instances. Most singleton instances is created by `double checked synchronized block`|
|`logging`|This is not setup yet. It should upload errors to server|
|`ui`|contains the Activities and Fragments . The hierarchy is followed as Application ==> Activities ==> Fragments. An Application is a single entity of the application. But it can have multiple Activities. Each Activity also can have multiple fragments.|
|`ViewModels`|Each Activity and Fragment has its own view model. View model connects data to view. Remember the FragmentViewModel will live as long as the Fragment but its containing Activity will persist. So you have to decide wisely which Viewmodel to use and when. Also note that it is possible for an Application to only use its `own` viewmodel (not implemented here) and not others. An Activity can access its `own` view model and `appViewModel`. A Fragment View Model can access all three view models. This is because of the heirarchy stated above |
|`config`|contains configuration keys|

