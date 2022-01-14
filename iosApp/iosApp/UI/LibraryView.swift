//
//  LibraryView.swift
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

struct LibraryListView: View {
    
   let items: [LibraryItem]
    
    var body: some View {
        List(items, id: \.id) {
            LibraryItemView(library: $0)
        }
    }
}

struct LibraryView: View {
    
    @StateObject var store = LibraryStore()
    
    var body: some View {
        LibraryListView(items: store.libraryItems).onAppear(perform: {
            store.start()
        })
    }
}

struct LibraryView_Previews: PreviewProvider {
    static var previews: some View {
        LibraryView()
    }
}
