import React from 'react';
import { Button, Card, List, message, Tabs, Tooltip } from 'antd';
import { StarOutlined, StarFilled } from '@ant-design/icons';
import { addFavoriteItem, deleteFavoriteItem } from '../utils';
//  tabKeys, url: place_name
const { TabPane } = Tabs;
const tabKeys = {
  DRIVE: 'Drive',
  TOUR: 'Tour',
  INTERNATIONAL: 'International',
}
//  If no url or image
const processName = (name) => name
  .replace('%{height}', '252')
  .replace('%{width}', '480')
  .replace('{height}', '252')
  .replace('{width}', '480');
 
 const renderCardTitle = (item, loggedIn, favs, favOnChange) => {
  const title = `${item.name} - ${item.formatted_address}`;
 
  const isFav = favs.find((fav) => fav.place_id === item.place_id);
 
  const favOnClick = () => {
    if (isFav) {
      deleteFavoriteItem(item).then(() => {
        favOnChange();
      }).catch(err => {
        message.error(err.message)
      })
 
      return;
    }
 
    addFavoriteItem(item).then(() => {
      favOnChange();
    }).catch(err => {
      message.error(err.message)
    })
  }
 
  return (
    <>
      {
        loggedIn &&
        <Tooltip title={isFav ? "Remove from favorite list" : "Add to favorite list"}>
          <Button shape="circle" icon={isFav ? <StarFilled /> : <StarOutlined />} onClick={favOnClick} />
        </Tooltip>
      }
      <div style={{ overflow: 'hidden', textOverflow: 'ellipsis', width: 450 }}>
        <Tooltip title={title}>
          <span>{title}</span>
        </Tooltip>
      </div>
    </>
  )
}
 
const renderCardGrid = (data, loggedIn, favs, favOnChange) => {
  return (
    <List
      grid={{
        xs: 1,
        sm: 2,
        md: 4,
        lg: 4,
        xl: 6,
      }}
      dataSource={data}
      renderItem={item => (
        <List.Item style={{ marginRight: '20px' }}>
          <Card
            title={renderCardTitle(item, loggedIn, favs, favOnChange)}
          >
            <a href={item.url} target="_blank" rel="noopener noreferrer">
              <img 
                alt="Placeholder"
                src={processUrl(item.name)}
              />
            </a>
          </Card>
        </List.Item>
      )}
    />
  )
}
 
const Home = ({ resources, loggedIn, favoriteItems, favoriteOnChange }) => {
  const { DRIVE, TOUR, INTERNATIONAL } = resources;
  const { DRIVE: favDrive, TOUR: favTour, INTERNATIONAL: favInternational} = favoriteItems;
 
  return (
    <Tabs 
      defaultActiveKey={tabKeys.Drive} 
    >
      <TabPane tab="Drive" key={tabKeys.DRIVE} style={{ height: '680px', overflow: 'auto' }} forceRender={true}>
        {renderCardGrid(DRIVE, loggedIn, favDrive, favoriteOnChange)}
      </TabPane>
      <TabPane tab="TOUR" key={tabKeys.TOUR} style={{ height: '680px', overflow: 'auto' }} forceRender={true}>
        {renderCardGrid(TOUR, loggedIn, favTour, favoriteOnChange)}
      </TabPane>
      <TabPane tab="INTERNATIONAL" key={tabKeys.INTERNATIONAL} style={{ height: '680px', overflow: 'auto' }} forceRender={true}>
        {renderCardGrid(INTERNATIONAL, loggedIn, favInternational, favoriteOnChange)}
      </TabPane>
    </Tabs>
  );
}
 
export default Home;
