import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Input, Select, Button, Card, Steps, Upload, message, Radio } from 'antd';
import { UploadOutlined, ArrowLeftOutlined } from '@ant-design/icons';
import { submissionAPI } from '../services/api';

const { Step } = Steps;
const { TextArea } = Input;

const categories = [
  { value: 'role_model', label: '榜样' },
  { value: 'text', label: '文字' },
  { value: 'animation', label: '动漫音视频' },
  { value: 'activity', label: '专题活动' },
  { value: 'image', label: '图片' },
];

const applicantTypes = [
  { value: 'individual', label: '个人申报' },
  { value: 'organization', label: '单位推荐' },
];

function SubmissionCreate() {
  const [currentStep, setCurrentStep] = useState(0);
  const [form] = Form.useForm();
  const [applicantType, setApplicantType] = useState('individual');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const normFile = (e) => {
    if (Array.isArray(e)) return e;
    return e?.fileList;
  };

  const handleSubmit = async () => {
    setLoading(true);
    try {
      // 获取所有字段值
      const values = form.getFieldsValue(true);
      
      // 验证必填字段
      if (!values.category) {
        message.error('请选择申报项目');
        setCurrentStep(0);
        setLoading(false);
        return;
      }
      if (!values.applicantType || !values.title || !values.description) {
        message.error('请完善基本信息');
        setCurrentStep(1);
        setLoading(false);
        return;
      }
      
      const res = await submissionAPI.create(values);
      message.success('创建成功');
      navigate('/');
    } catch (error) {
      message.error(error.response?.data?.message || '创建失败');
    }
    setLoading(false);
  };

  const renderStepContent = () => {
    return (
      <>
        {/* 第一步：选择项目 - 始终渲染但可能隐藏 */}
        <div style={{ display: currentStep === 0 ? 'block' : 'none' }}>
          <Form.Item name="category" rules={[{ required: true, message: '请选择申报项目' }]}>
            <Select placeholder="选择申报项目" options={categories} />
          </Form.Item>
        </div>

        {/* 第二步：基本信息 - 始终渲染但可能隐藏 */}
        <div style={{ display: currentStep === 1 ? 'block' : 'none' }}>
          <Form.Item name="applicantType" rules={[{ required: true }]}>
            <Radio.Group onChange={(e) => setApplicantType(e.target.value)}>
              {applicantTypes.map(type => (
                <Radio.Button key={type.value} value={type.value}>{type.label}</Radio.Button>
              ))}
            </Radio.Group>
          </Form.Item>
          <Form.Item name="title" rules={[{ required: true, message: '请输入作品名称' }]}>
            <Input placeholder="作品名称" />
          </Form.Item>
          <Form.Item name="description" rules={[{ required: true, message: '请输入作品描述' }]}>
            <TextArea rows={4} placeholder="作品描述" />
          </Form.Item>
        </div>

        {/* 第三步：详细信息 - 始终渲染但可能隐藏 */}
        <div style={{ display: currentStep === 2 ? 'block' : 'none' }}>
          {applicantType === 'organization' && (
            <>
              <Form.Item name="recommendUnit" rules={[{ required: true, message: '请输入推荐单位' }]}>
                <Input placeholder="推荐单位" />
              </Form.Item>
              <Form.Item name="sealFile" valuePropName="fileList" getValueFromEvent={normFile}>
                <Upload name="seal" action="http://localhost:8080/api/upload" listType="text">
                  <Button icon={<UploadOutlined />}>上传单位推荐盖章</Button>
                </Upload>
              </Form.Item>
            </>
          )}
          <Form.Item name="workLink" rules={[{ type: 'url', message: '请输入有效的URL' }]}>
            <Input placeholder="作品网络链接（可选）" />
          </Form.Item>
          <Form.Item name="workFiles" valuePropName="fileList" getValueFromEvent={normFile}>
            <Upload name="files" action="http://localhost:8080/api/upload" multiple listType="text">
              <Button icon={<UploadOutlined />}>上传相关文件</Button>
            </Upload>
          </Form.Item>
        </div>
      </>
    );
  };

  return (
    <div className="page-container">
      <Card title="创建申报" className="card-shadow">
        <Button icon={<ArrowLeftOutlined />} onClick={() => navigate('/')} style={{ marginBottom: 16 }}>
          返回
        </Button>
        <Steps current={currentStep} style={{ marginBottom: 24 }}>
          {['选择项目', '基本信息', '详细信息'].map(title => <Step key={title} title={title} />)}
        </Steps>
        <Form form={form} layout="vertical">
          {renderStepContent()}
          <Form.Item>
            <div style={{ display: 'flex', gap: 12, justifyContent: 'flex-end' }}>
              {currentStep > 0 && (
                <Button onClick={() => setCurrentStep(currentStep - 1)}>上一步</Button>
              )}
              {currentStep < 2 ? (
                <Button type="primary" onClick={() => setCurrentStep(currentStep + 1)}>下一步</Button>
              ) : (
                <Button type="primary" onClick={handleSubmit} loading={loading}>保存</Button>
              )}
            </div>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
}

export default SubmissionCreate;