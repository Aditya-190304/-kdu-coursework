import { Component, type ErrorInfo, type ReactNode } from 'react';

export default class ErrorBoundary extends Component<{ children?: ReactNode }, { hasError: boolean }> 
{
  state = { hasError: false };
  static getDerivedStateFromError() { 
    return { hasError: true }; 
}
  componentDidCatch(error: Error, info: ErrorInfo) { 
    console.error('Caught error:', error, info); 
}
  render() {
    if (this.state.hasError) return <div role="alert"><h2>something went wrong.</h2></div>;
    return this.props.children;
  }
}