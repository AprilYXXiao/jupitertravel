import React from 'react';
import { Button, Form, Input, message, Modal } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { searchGameByName } from '../utils';
 
class CustomSearch extends React.Component {
  state = {
    displayModal: false
  }
 
  handleCancel = () => {
    this.setState({
      displayModal: false,
    })
  }
 
  searchOnClick = () => {
    this.setState({
      displayModal: true,
    })
  }
  // search place by name: data > name
  onSubmit = (data) => {
    searchPlaceByName(data.name)
      .then((data) => {
        this.setState({
          displayModal: false,
        })
        this.props.onSuccess(data);
      })
      .catch((err) => {
        message.error(err.message);
      })
  }
 
  render = () => {
    return (
      <>
        <Button shape="round" onClick={this.searchOnClick} icon={<SearchOutlined />} style={{ marginLeft: '20px', marginTop: '20px'}}>
          Search Place</Button>
        <Modal
          title="Search"
          visible={this.state.displayModal}
          onCancel={this.handleCancel}
          footer={null}
        >
          <Form
            name="custom_search"
            onFinish={this.onSubmit}
          >
            {/* //Form.Item: place_name */}
            <Form.Item
              name="place_name"
              rules={[{ required: true, message: 'Please enter the place that you are interested' }]}
            >
              <Input placeholder="Place to go" />
            </Form.Item>
 
            <Form.Item>
              <Button type="primary" htmlType="submit">
                Search</Button>
            </Form.Item>
          </Form>
        </Modal>
      </>
    )
  }
}
 
export default CustomSearch;
