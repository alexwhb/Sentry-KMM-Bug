import SwiftUI
import shared

let utils = Utils()

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            ContentView().onAppear {
                utils.setupSentry(context: nil, isDebug: false, releaseVersion: "1.0.0")
            }
		}
	}
}
