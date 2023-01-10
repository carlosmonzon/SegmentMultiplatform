// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/carlosmonzon/SegmentMultiplatform/releases/assets/91125818.zip"
let remoteKotlinChecksum = "e16bf6ac912a53a4a2582cdb8df7c9070ce3b0589b6213ab322fb3f9be46711e"
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