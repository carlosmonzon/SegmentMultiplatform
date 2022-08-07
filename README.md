# Segment multiplatform (experimental)

Current support for iOS and Android only

## iOS

### Create Swift Package

```
./gradlew createSwiftPackage
```

Look at directory `swiftpackage`
Current package is pushed to a separate repository: https://github.com/carlosmonzon/SegmentMultiplatformPackage

```
./gradlew assembleXCFramework
```

Look at directory `/build/XCFramework`

## Segment Framework

Current approach uses Segment framework with cinterop which follows kotlin multiplatform guide to add a framework
without cocoapods

https://kotlinlang.org/docs/multiplatform-mobile-ios-dependencies.html#add-a-framework-without-cocoapods

When trying to use segment as a cocoapods dependency I found some issues related to Segment headers
containing `@import Foundation;` instead of `#import <Foundation/Foundation.h>` which I couldn't resolve, hence adding
the Segment framework

### How to replace the Segment.xcframework native resource? (Current version 4.1.6)

Download https://github.com/segmentio/analytics-ios

Download script: https://gist.github.com/carlosmonzon/0b775d3aa15d9d0d5c05194515c90f8f and put it in the segment root
folder

Run the script and `Segment_XCFramework` will be created. Replace the content inside
the `SegmentMultiplatform/src/nativeInterop/cinterop/Segment`

## Android

### Publish to Maven Local

Run the following command to publish the artifacts to local maven

```
./gradlew publishToMavenLocal
```

### Add android dependency to project

```kotlin
implementation("org.monzon:SegmentMultiplatform-android::${version}")
```






