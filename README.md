# CurrencyExchangeApp

Test task is in master branch

Clean architecture with separating app into domain, data and feature modules is appliedm

Domain logic is in UseCases - atomic business rules

MVVI pattern based on ViewModel from Jetpack Arch Components is applied for Presentation layer

The arch chanin for handling business logic with access to data layer - Interactor -> UseCases -> Repository(a facade for data sources) -> DataSources(Room Db, Preferences DataStore, BE Rest api) 

Koin for DI

Interactors are injected to VM. It's a kind of facade for feature(usually a single Interactor per VM + some specific Interactors sometimes) that handle sequences (from one up to many) of usecases

Validation rules (NegativeBalanceValidationUseCase only in the current app) are managed by ValidationUseCase instances and ValidationAggregator that handles a list of ValidationUseCase for certain case

Exchange Fee calculation is implemented using Decorator pattern based on ExchangeFeeDecorator interface

I placed Validation and ExchangeFeeDecorators into feature module but it's a point to discuss to move them into domain
