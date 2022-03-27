//
//  ImageView.swift
//  iosApp
//
//  Created by Harsewak Singh on 26/03/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI


struct ImageView: View {
    
    var url: String?
    
    var body: some View {
        if let stringUrl = url, let url = URL(string: stringUrl) {
            Image(systemName: Resources.images.home).data(url: url).frame(width: 150, height: 100, alignment: .center).scaledToFill()
        } else {
            Image(systemName: Resources.images.home).clipShape(RoundedRectangle(cornerRadius: 10)).shadow(radius: 10).scaledToFit()
        }
    }
}

struct ImageView_Previews: PreviewProvider {
    static var previews: some View {
        ImageView(url: "https://djpunjab.video/covers/15461-0.jpg")
    }
}
