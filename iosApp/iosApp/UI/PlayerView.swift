//
//  PlayerView.swift
//  iosApp
//
//  Created by Harsewak Singh on 21/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import AVKit
private let MIN_HEIGHT = CGFloat(60)

struct PlayerView: View {
    @EnvironmentObject var player: Player
    @State var playing: Bool = true
    
    @State var height: CGFloat = MIN_HEIGHT
    @State var smallVIew: Bool = true
    
    var body: some View {
        ZStack(alignment: .center) {
            Color.green.opacity(0.1).edgesIgnoringSafeArea(.all)
            
            if smallVIew {
                HStack {
                    Spacer()
                    Image(systemName:    Resources.images.home).load(url: player.track?.thumbnail).resizable()
                        .aspectRatio(contentMode: .fit).scaledToFit()
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
            } else {
                VStack {
                    Spacer()
                    Image(systemName:    Resources.images.home).load(url: player.track?.thumbnail).resizable()
                        .aspectRatio(1.0, contentMode: .fit).scaledToFit()
                    Spacer()
                    Text("\(player.track?.name ?? "This is long text title large")").font(.body).lineLimit(1)
                    Text("\(player.track?.name ?? "")").font(.callout).lineLimit(1).previewDisplayName("This is long text title small")
                    
                    
                    Spacer()
                    Button(action: {
                        player.toggle()
                        playing.toggle()
                    }) {
                        Image(systemName: playing ? "pause.circle.fill" : "play.circle.fill").resizable()
                            .frame(width: 50, height: 50)
                            .aspectRatio(contentMode: .fit)
                    }.padding()
                    
                }
            }
            
        }.frame(minHeight: height, maxHeight: height)
            .gesture(
                DragGesture()
                    .onChanged { value in
                        let maxHeight = UIScreen.screenHeight - 120
                        let measureHeight = max(MIN_HEIGHT, height - value.translation.height)
                        if measureHeight <= maxHeight {
                            height = measureHeight
                        }
                        smallVIew = height <= maxHeight / 2
                    }
            )
    }
}
struct PlayerView_Previews: PreviewProvider {
    static var previews: some View {
        PlayerView().environmentObject(Player())
    }
}
