//
//  MainView.swift
//  iosApp
//
//  Created by Harsewak Singh on 21/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MainView: View {
    @EnvironmentObject var player: Player
    @State var playing: Bool = false
    
    var body: some View {
        ZStack(alignment: .bottom) {
            VStack {
                TabView {
                    HomeView()
                        .tabItem {
                            Image(systemName: Resources.images.home)
                            Text(Resources.strings.home)
                        }
                    Text("Explore")
                        .tabItem {
                            Image(systemName: Resources.images.explore)
                            Text(Resources.strings.explore)
                        }
                    LibraryView()
                        .tabItem {
                            Image(systemName: Resources.images.library)
                            Text(Resources.strings.library)
                        }
                }
            }.onChange(of: player.track, perform: {
                newState in
                playing = newState != nil
            })
            if(playing){
                PlayerView().padding(.bottom, 60)
            }
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
