//
//  PlayerView.swift
//  iosApp
//
//  Created by Harsewak Singh on 21/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import AVKit

struct PlayerView: View {
    @EnvironmentObject var player: Player
    @State var playing: Bool = true
    
    var body: some View {
        ZStack(alignment: .center) {
            Color.green.opacity(0.1).edgesIgnoringSafeArea(.all)
            HStack {
                Image(systemName: Resources.images.home).resizable()
                    .aspectRatio(contentMode: .fit).padding(.leading, 8).padding(.top, 8).padding(.bottom, 8)
                
                VStack {
                    Text("\(player.track?.name ?? "This is long text title")").font(.body).lineLimit(1)
                    Text("\(player.track?.name ?? "")").font(.callout).lineLimit(1).previewDisplayName("This is long text title")

                }.padding(.leading, 5)
                Spacer()
                Button(action: {
                    player.toggle()
                    playing.toggle()
                }) {
                    Image(systemName: playing ? "pause.circle.fill" : "play.circle.fill").resizable()
                        .frame(width: 25, height: 25)
                        .aspectRatio(contentMode: .fit)
                }.padding()
                
            }
            
        }.frame(minHeight: 60, maxHeight: 60).cornerRadius(10).padding(.leading, 5).padding(.trailing, 5)
    }
}
struct PlayerView_Previews: PreviewProvider {
    static var previews: some View {
        PlayerView().environmentObject(Player())
    }
}
