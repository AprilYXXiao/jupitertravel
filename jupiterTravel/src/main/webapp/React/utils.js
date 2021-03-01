// If you don't want to host your server code and client code together, you can 
// pay AWS to host your server with HTTPS then config the api url endpoints like below
// const SERVER_ORIGIN = '<Your server's url>'; 
const SERVER_ORIGIN = '';
 
const loginUrl = `${SERVER_ORIGIN}/login`;
 
export const login = (credential) => {
  return fetch(loginUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify(credential)
  }).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to log in');
    }
 
    return response.json();
  })
}
 
const registerUrl = `${SERVER_ORIGIN}/register`;
 
export const register = (data) => {
  return fetch(registerUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data)
  }).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to register');
    }
  })
}
 
const logoutUrl = `${SERVER_ORIGIN}/logout`;
 
export const logout = () => {
  return fetch(logoutUrl, {
    method: 'POST',
    credentials: 'include',
  }).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to log out');
    }
  })
}

// get top places: response.json(ID), url; might got SF places
const topPlace = `${SERVER_ORIGIN}/place`;
// const topGamesUrl = `${SERVER_ORIGIN}/game`;
 
// sevelet not changed
export const getTopPlaces = () => {
  return fetch(topPlacesName).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to get top place');
    }
 
    return response.json();
  })
}

// GetplacesDetails: response.jason(name) place_name replace url
const getPlaceDetailsName = `${SERVER_ORIGIN}/place?place_name=`;
 
const getPlaceDetails = (placeName) => {
  return fetch(`${getPlaceDetailsUrl}${placeName}`).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to find the place');
    }
 
    return response.json();
  });
}
//  searchplacesByName: Place name, could delete
const searchPlaceByIdUrl = `${SERVER_ORIGIN}/search?place_id=`;
 
export const searchPlaceById = (placeId) => {
  return fetch(`${searchPlaceByIdUrl}${placeId}`).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to find the place');
    }
    return response.json();
  })
}
 
export const searchPlaceByName = (placeName) => {
  return getPlaceDetails(placeName).then((data) => {
    if (data && data.id) {
      return searchPlaceById(data.id);
    }
 
    throw Error('Fail to find the place')
  })
}
 
const favoriteItemUrl = `${SERVER_ORIGIN}/favorite`;
 
export const addFavoriteItem = (favItem) => {
  return fetch(favoriteItemUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify({ favorite: favItem })
  }).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to add favorite item');
    }
  })
}
 
export const deleteFavoriteItem = (favItem) => {
  return fetch(favoriteItemUrl, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify({ favorite: favItem })
  }).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to delete favorite item');
    }
  })
}
 
export const getFavoriteItem = () => {
  return fetch(favoriteItemUrl, {
    credentials: 'include',
  }).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to get favorite item');
    }
 
    return response.json();
  })
}
 
const getRecommendedItemsUrl = `${SERVER_ORIGIN}/recommendation`;
 
export const getRecommendations = () => {
  return fetch(getRecommendedItemsUrl, {
    credentials: 'include',
  }).then((response) => {
    if (response.status !== 200) {
      throw Error('Fail to get recommended item');
    }
 
    return response.json();
  })
}