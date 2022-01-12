//
//  LibraryView.swift
//  iosApp
//
//  Created by Harsewak Singh on 11/01/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

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
