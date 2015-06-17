== 0.5.0 ====================================

  Fixed

    . UriQueryParser by adding support to duplicate key values for better URI compatibility.

    . User agent construction by abstracting string formatting from locale

    . Background thread executor priority


  Added

    . UriQueryPair

    . Unit tests for UriQueryParser & UriQueryPair.
    


== 0.4.1 ====================================

  Fixed

    . Non-parsed query pairs with empty value issue



== 0.4.0 ====================================

  Added

    . UriQueryParser, for memory efficient alternative for query parameter access

    . Screen layout API to access masked screen size configuration dynamically.



== 0.3.0 ====================================

  Added

    . Accessor for W3 compatible device specific user agent accessor to Device class.



== 0.2.0 ====================================

  Added

    . Pinterest share APIs.
    . Instagram share APIs, although not working with remote URLs. Only with local resources :(.
    . Display APIs
    . Ancient camera APIs.

  Updated

    . Camera APIs now accept SurfaceHolder itself from user, instead of creating a dummy instance.
    . Moved display related APIs from Device to Display class && Added status bar height accessor to Display



== 0.1.0 ====================================

  Updated

    . Android plugin version to 1.0.0
    . StringHelper with Apache Commons Lang counterpart StringUtils. Thus commons-lang package became a dependency, from now on be sure to apply proguard ^^

  Added

    . Social share APIs.
    . GooglePlayServices as a dependency for Google+ share best compatibility. Again, remember, proguard!
    . Apache Commons Lang 3.3.+ as a dependency.
    . Optimized executor APIs.


  !!!: Note that Camera Utilities with this version is not production ready and will be fully integrated in following versions.



== 0.0.2 ====================================

  - Added APIs for showing and hiding keyboard.



== 0.0.1 ====================================

  - Initial release with

    . device
    . storage
    . battery

    APIs.
