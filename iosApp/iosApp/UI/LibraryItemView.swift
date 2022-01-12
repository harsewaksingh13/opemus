//
//  LibraryItemView.swift
//  iosApp
//
//  Created by Harsewak Singh on 11/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

typealias LibraryItem = shared.LibraryItem
typealias LibraryType = shared.LibraryType
typealias TrackManager = shared.TrackManager
typealias TrackManagerImpl = shared.TrackManagerImpl
typealias ServiceManager = shared.ServiceManager
typealias Services = shared.Services

let LibraryPreviewItems = LibraryItem.Companion.shared.previewItems

struct LibraryItemView: View {
    
    let library: LibraryItem
    
    var body: some View {
        HStack {
            Text(library.name.name)
            Spacer()
        }
    }
}

struct LibraryItemView_Previews: PreviewProvider {
    static var previews: some View {
        LibraryItemView(library: LibraryPreviewItems.first!)
    }
}
