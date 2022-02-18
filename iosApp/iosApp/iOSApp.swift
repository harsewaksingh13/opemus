import SwiftUI

@main
struct iOSApp: App {
    @StateObject var player = Player()
    
	var body: some Scene {
		WindowGroup {
            MainView().environmentObject(player)
        }
	}
}
