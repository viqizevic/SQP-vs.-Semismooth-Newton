function test_dixon_3_dim_func_with_sqp_octave_too(show)
    a = [-10; -10; -10];
    b = [10; 10; 10];
    x0 = [1.5; 1.5; 1.5];
    tol = 0.001;
    itmax = 500;
    G = [];
    r = [];
    G = [ G; -eye(length(a)); eye(length(b)) ];
    r = [ r; -a; b ];
    tic;
    %[x_ssn,fval_ssn,it_ssn] = active_set_strategy('dixon_3_dim_func','grad_dixon_3_dim_func','hess_dixon_3_dim_func',G,r,x0,itmax,tol);
    [x_ssn,fval_ssn,it_ssn] = semismooth_newton('dixon_3_dim_func','grad_dixon_3_dim_func','hess_dixon_3_dim_func',G,r,x0,itmax,tol);
    t_ssn = toc;
    x1 = sprintf('%.3f ',x_ssn);
    f1 = sprintf('f(x_ssn) = %.3f',fval_ssn);
    t1 = sprintf('solved in %.2f ms.',t_ssn*1000);
    str1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];
    tic;
    [x_sqp,fval_sqp,it_sqp] = seq_quad_prog('dixon_3_dim_func','grad_dixon_3_dim_func','hess_dixon_3_dim_func',G,r,x0,itmax,tol);
    t_sqp = toc;
    x2 = sprintf('%.3f ',x_sqp);
    f2 = sprintf('f(x_sqp) = %.3f',fval_sqp);
    t2 = sprintf('solved in %.2f ms.',t_sqp*1000);
    str2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];
    tic;
    [x_oct,fval_oct,info_oct,it_oct] = sqp(x0,@phi,[],@g);
    t_oct = toc;
    x3 = sprintf('%.3f ',x_oct);
    f3 = sprintf('f(x_oct) = %.3f',fval_oct);
    t3 = sprintf('solved in %.2f ms.',t_oct*1000);
    str3 = ['x_oct = [ ', x3, '], ', f3, ', it = ', num2str(it_oct), ', ', t3];
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
        disp(str3);
    end
end

function obj = phi(x)
    obj = dixon_3_dim_func(x);
end

function s = g(x)
    a = [-10; -10; -10];
    b = [10; 10; 10];
    x0 = [1.5; 1.5; 1.5];
    tol = 0.001;
    itmax = 500;
    G = [];
    r = [];
    G = [ G; -eye(length(a)); eye(length(b)) ];
    r = [ r; -a; b ];
    s = r - G*x;
end
