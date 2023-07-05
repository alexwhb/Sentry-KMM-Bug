import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
        VStack {
            Button(action: {utils.crash()}, label: {
                Text("crash me")})
            Button(action: {utils.logError()}, label: {Text("Log Error")})
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
