import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Table, Button, Tag, Card, Modal, message, Empty } from 'antd';
import { PlusOutlined, EyeOutlined, EditOutlined, DeleteOutlined, UndoOutlined } from '@ant-design/icons';
import { submissionAPI } from '../services/api';

function Home() {
  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isDeadlinePassed, setIsDeadlinePassed] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    loadSubmissions();
    checkDeadline();
  }, []);

  const checkDeadline = () => {
    const deadline = localStorage.getItem('deadline') || '2026-12-31';
    const now = new Date();
    const deadlineDate = new Date(deadline);
    setIsDeadlinePassed(now > deadlineDate);
  };

  const loadSubmissions = async () => {
    try {
      const res = await submissionAPI.getList();
      setSubmissions(res.data.data || []);
    } catch (error) {
      message.error('加载申报列表失败');
    }
    setLoading(false);
  };

  const handleDelete = (id) => {
    Modal.confirm({
      title: '确认删除',
      content: '删除后无法恢复，是否继续？',
      onOk: async () => {
        try {
          await submissionAPI.delete(id);
          message.success('删除成功');
          loadSubmissions();
        } catch (error) {
          message.error('删除失败');
        }
      },
    });
  };

  const handleWithdraw = async (id) => {
    try {
      await submissionAPI.withdraw(id);
      message.success('撤回成功');
      loadSubmissions();
    } catch (error) {
      message.error('撤回失败');
    }
  };

  const handleSubmit = async (id) => {
    try {
      await submissionAPI.submit(id);
      message.success('提交成功');
      loadSubmissions();
    } catch (error) {
      message.error(error.response?.data?.message || '提交失败');
    }
  };

  const getStatusTag = (status) => {
    switch (status) {
      case 'draft':
        return <Tag className="status-badge status-draft">未提交</Tag>;
      case 'submitted':
        return <Tag className="status-badge status-submitted">已提交</Tag>;
      default:
        return <Tag>{status}</Tag>;
    }
  };

  const columns = [
    {
      title: '申报项目',
      dataIndex: 'category',
      key: 'category',
      render: (text) => {
        const categories = {
          role_model: '榜样',
          text: '文字',
          animation: '动漫音视频',
          activity: '专题活动',
          image: '图片',
        };
        return categories[text] || text;
      },
    },
    {
      title: '作品名称',
      dataIndex: 'title',
      key: 'title',
    },
    {
      title: '当前状态',
      dataIndex: 'status',
      key: 'status',
      render: getStatusTag,
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      key: 'createTime',
      render: (text) => text ? new Date(text).toLocaleString('zh-CN') : '-',
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => {
        const isDraft = record.status === 'draft';
        const isSubmitted = record.status === 'submitted';

        return (
          <div style={{ display: 'flex', gap: 8 }}>
            {isDraft && !isDeadlinePassed && (
              <>
                <Button size="small" icon={<EditOutlined />} onClick={() => navigate(`/detail/${record.id}`)}>
                  编辑
                </Button>
                <Button size="small" type="primary" onClick={() => handleSubmit(record.id)}>
                  提交
                </Button>
                <Button size="small" danger icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)}>
                  删除
                </Button>
              </>
            )}
            {isSubmitted && (
              <>
                <Button size="small" icon={<EyeOutlined />} onClick={() => navigate(`/detail/${record.id}`)}>
                  查看
                </Button>
                <Button size="small" icon={<UndoOutlined />} onClick={() => handleWithdraw(record.id)}>
                  撤回
                </Button>
              </>
            )}
            {isDeadlinePassed && (
              <Button size="small" icon={<EyeOutlined />} onClick={() => navigate(`/detail/${record.id}`)}>
                查看
              </Button>
            )}
          </div>
        );
      },
    },
  ];

  return (
    <div className="page-container">
      <Card
        title="我的申报"
        extra={
          !isDeadlinePassed && (
            <Button type="primary" icon={<PlusOutlined />} onClick={() => navigate('/create')}>
              创建新的申报
            </Button>
          )
        }
      >
        {isDeadlinePassed && (
          <div style={{ background: '#fff7e6', border: '1px solid #ffd591', padding: '12px', borderRadius: '4px', marginBottom: '16px' }}>
            <strong>申报已结束</strong> - 申报截止时间已到，已关闭所有提交入口。
          </div>
        )}
        <Table
          dataSource={submissions}
          columns={columns}
          rowKey="id"
          loading={loading}
          locale={{ emptyText: <Empty description="暂无申报记录" /> }}
        />
      </Card>
    </div>
  );
}

export default Home;