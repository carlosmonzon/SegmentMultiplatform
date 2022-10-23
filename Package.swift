// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/carlosmonzon/SegmentMultiplatform/releases/assets/81945643.zip"
let remoteKotlinChecksum = "df2de5eb2611ce107af071524b8b39897811dcbc470a42d830b480aa2a05b951"
let packageName = "SegmentMultiplatform"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)