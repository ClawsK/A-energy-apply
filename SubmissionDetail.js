import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Input, Select, Button, Card, Upload, message, Tag, Divider } from 'antd';
import { ArrowLeftOutlined, SaveOutlined, UploadOutlined } from '@ant-design/icons';
import { submissionAPI } from '../services/api';

const { TextArea } = Input;

const categories = {
  role_model: '榜样',
  text: '文字',
  animation: '动漫音视频',
  activity: '专题活动',
  image: '图片',
};

function SubmissionDetail() {
  const { id } = useParams();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [submission, setSubmission] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadSubmission();
  }, [id]);

  const loadSubmission = async () => {
    try {
      const res = await submissionAPI.getDetail(id);
      const data = res.data.data;
      setSubmission(data);
      form.setFieldsValue(data);
    } catch (error) {
      message.error('加载失败');
    }
    setLoading(false);
  };

  const handleSave = async (values) => {
    setSaving(true);
    try {
      await submissionAPI.update(id, values);
      message.success('保存成功');
    } catch (error) {
      message.error('保存失败');
    }
    setSaving(false);
  };

  if (loading) return <div style={{ textAlign: 'center', padding: 50 }}>加载中...</div>;
  if (!submission) return <div>申报不存在</div>;

  const isDraft = submission.status === 'draft';

  return (
    <div className="page-container">
      <Card className="card-shadow">
        <Button icon={<ArrowLeftOutlined />} onClick={() => navigate('/')} style={{ marginBottom: 16 }}>
          返回
        </Button>
        <div style={{ marginBottom: 16 }}>
          <h2 style={{ margin: 0, display: 'inline' }}>申报详情</h2>
          <Tag style={{ marginLeft: 12 }} color={submission.status === 'draft' ? 'orange' : 'green'}>
            {submission.status === 'draft' ? '未提交' : '已提交'}
          </Tag>
        </div>
        <Divider />
        <Form form={form} onFinish={handleSave} layout="vertical" disabled={!isDraft}>
          <div className="form-section">
            <div className="form-section-title">项目信息</div>
            <Form.Item label="申报类别">
              <Input value={categories[submission.category]} disabled />
            </Form.Item>
          </div>

          <div className="form-section">
            <div className="form-section-title">个人信息</div>
            <Form.Item name="title" label="作品名称" rules={[{ required: true }]}>
              <Input />
            </Form.Item>
            <Form.Item name="description" label="作品描述" rules={[{ required: true }]}>
              <TextArea rows={4} />
            </Form.Item>
          </div>

          <div className="form-section">
            <div className="form-section-title">推荐信息</div>
            {submission.applicantType === 'organization' && (
              <>
                <Form.Item name="recommendUnit" label="推荐单位" rules={[{ required: true }]}>
                  <Input />
                </Form.Item>
                <Form.Item name="sealFile" label="单位推荐盖章">
                  <Upload disabled>
                    <Button icon={<UploadOutlined />} disabled>查看盖章文件</Button>
                  </Upload>
                </Form.Item>
              </>
            )}
            {submission.applicantType === 'individual' && (
              <Form.Item name="idCard" label="身份证信息">
                <Input disabled />
              </Form.Item>
            )}
          </div>

          <div className="form-section">
            <div className="form-section-title">作品信息</div>
            <Form.Item name="workLink" label="网络链接">
              <Input />
            </Form.Item>
            <Form.Item name="workFiles" label="相关文件">
              <Upload disabled>
                <Button icon={<UploadOutlined />} disabled>查看文件</Button>
              </Upload>
            </Form.Item>
          </div>

          {isDraft && (
            <Form.Item>
              <Button type="primary" htmlType="submit" loading={saving} icon={<SaveOutlined />}>
                保存修改
              </Button>
            </Form.Item>
          )}
        </Form>
      </Card>
    </div>
  );
}

export default SubmissionDetail;