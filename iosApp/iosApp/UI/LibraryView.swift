//
//  LibraryView.swift
//  iosApp
//
//  Created by Harsewak Singh on 11/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

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
    
    @ObservedObject var store: LibraryStore
    
    var body: some View {
        NavigationView {
            List(store.libraryItems, id: \.id) { libraryItem in
                NavigationLink {
                    if libraryItem.isSongs {
                        SongsView(tracks: store.songs)
                    }
                } label: {
                    LibraryItemView(library: libraryItem)
                }
            }
        }
    }
}

struct LibraryView: View {
    
    @StateObject var store = LibraryStore()
    
    var body: some View {
        LibraryListView(store: store).onAppear(perform: {
            store.start()
        })
    }
}

struct LibraryView_Previews: PreviewProvider {
    static var previews: some View {
        LibraryView()
    }
}
