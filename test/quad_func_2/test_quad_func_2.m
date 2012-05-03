function [x_ssn,it_ssn,t_ssn,x_sqp,it_sqp,t_sqp] = test_quad_func_2(show)
    a = [3; 6];
    b = [10; 20];
    x0 = [8; 17];
    tol = 0.001;
    itmax = 100;
    G = [ -eye(length(a)); eye(length(b)) ];
    r = [ -a; b ];
    tic;
    %[x_ssn,fval_ssn,it_ssn] = active_set_strategy('quad_func_2','grad_quad_func_2','hess_quad_func_2',G,r,x0,itmax,tol);
    [x_ssn,fval_ssn,it_ssn] = semismooth_newton('quad_func_2','grad_quad_func_2','hess_quad_func_2',G,r,x0,itmax,tol);
    t_ssn = toc;
    x1 = sprintf('%.3f ',x_ssn);
    f1 = sprintf('f(x_ssn) = %.3f',fval_ssn);
    t1 = sprintf('solved in %.2f ms.',t_ssn*1000);
    str1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];
    tic;
    [x_sqp,fval_sqp,it_sqp] = seq_quad_prog('quad_func_2','grad_quad_func_2','hess_quad_func_2',G,r,x0,itmax,tol);
    t_sqp = toc;
    x2 = sprintf('%.3f ',x_sqp);
    f2 = sprintf('f(x_sqp) = %.3f',fval_sqp);
    t2 = sprintf('solved in %.2f ms.',t_sqp*1000);
    str2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];
    if ( nargin == 0 )
        show = 1;
    end
    if ( show == 1 )
        a = sprintf('%.3f ',a);
        b = sprintf('%.3f ',b);
        x0 = sprintf('%.3f ',x0);
        str0 = ['a = [ ', a, '], b = [ ', b, '], x0 = [ ', x0, ']'];
        disp(str0);
        disp(str1);
        disp(str2);
    end
end
