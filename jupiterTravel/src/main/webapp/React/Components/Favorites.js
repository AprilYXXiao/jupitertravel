import React from 'react';
import { Menu, Button, Drawer } from 'antd';
import { EyeOutlined, YoutubeOutlined, VideoCameraOutlined, StarFilled } from '@ant-design/icons';
//  MenueKey: place
const { SubMenu } = Menu;
const MenuKey = {
  DRIVE: 'Drive',
  TOUR: 'Tour',
  INTERNATIONAL: 'International'
}
class Favorites extends React.Component {
  state = {
    displayDrawer: false,
  }
 
  onDrawerClose = () => {
    this.setState({
      displayDrawer: false,
    })
  }
 
  onFavoriteClick = () => {
    this.setState({
      displayDrawer: true,
    })
  }
//  render place data (name), sub menue keep one
  render = () => {
    const { DRIVE, TOUR, INTERNATIONAL } = this.props.data;
 
    return (
      <>
        <Button type="primary" shape="round" onClick={this.onFavoriteClick} icon={<StarFilled />}>
          My Favorites</Button>
        <Drawer
          title="My Favorites"
          placement="right"
          width={720}
          visible={this.state.displayDrawer}
          onClose={this.onDrawerClose}
        >
          <Menu
            mode="inline"
            defaultOpenKeys={[MenuKey.DRIVE]}
            style={{ height: '100%', borderRight: 0 }}
            selectable={false}
          >
            <SubMenu key={MenuKey.DRIVE} icon={<EyeOutlined />} title="Drive">
              {
                DRIVE.map((item) => {
                  return (
                    <Menu.Item key={item.place_id}>
                      {/* place info: image? name */}
                      <h1 > {`${item.name} - ${item.formatted_address}`}</h1>
                      {/* <a href={item.url} target="_blank" rel="noopener noreferrer">
                        {`${item.broadcaster_name} - ${item.title}`}
                      </a> */}
                    </Menu.Item>
                  )
                })
              }
            </SubMenu>
            <SubMenu key={MenuKey.TOUR} icon={<YoutubeOutlined />} title="Tour">
              {
                TOUR.map((item) => {
                  return (
                    <Menu.Item key={item.place_id}>
                      <h1 > {`${item.name} - ${item.formatted_address}`}</h1>
                      {/* <a href={item.url} target="_blank" rel="noopener noreferrer">
                        {`${item.broadcaster_name} - ${item.title}`}
                      </a> */}
                    </Menu.Item>
                  )
                })
              }
            </SubMenu>
            <SubMenu key={MenuKey.INTERNATIONAL} icon={<VideoCameraOutlined />} title="International">
              {
                INTERNATIONAL.map((item) => {
                  return (
                    <Menu.Item key={item.place_id}>
                      <h1 > {`${item.name} - ${item.formatted_address}`}</h1>
                      {/* <a href={item.url} target="_blank" rel="noopener noreferrer">
                        {`${item.broadcaster_name} - ${item.title}`}
                      </a> */}
                    </Menu.Item>
                  )
                })
              }
            </SubMenu>
          </Menu>
        </Drawer>
      </>
    )
  }
}
 
export default Favorites;
