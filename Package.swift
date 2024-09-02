// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorPluginHealthConnect",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorPluginHealthConnect",
            targets: ["HealthConnectPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "HealthConnectPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/HealthConnectPlugin"),
        .testTarget(
            name: "HealthConnectPluginTests",
            dependencies: ["HealthConnectPlugin"],
            path: "ios/Tests/HealthConnectPluginTests")
    ]
)
