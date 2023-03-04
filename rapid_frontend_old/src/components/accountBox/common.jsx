import styled from "styled-components";

export const BoxContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
`;

export const FormContainer = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  border: none;
`;

export const MutedLink = styled.a`
  font-size: 11px;
  color: rgba(128, 128, 128, 1);
  font-weight: 500;
  text-decoration: none;
`;

export const BoldLink = styled.a`
  font-size: 13px;
  color: rgb(255, 128, 0);
  font-weight: 500;
  text-decoration: none;
  margin: 0 4px;
`;

export const Input = styled.input`
  width: 100%;
  height: 42px;
  outline: none;
  border: 1px solid rgba(200, 200, 200, 0.3);
  padding: 0 10px;
  border-bottom: 1.4px solid transparent;
  transition: all 200ms ease-in-out;
  font-size: 12px;
  margin-bottom: 4px;
  border-radius: 25px;

  &::placeholder {
    color: rgba(128, 128, 128, 1);
  }

  & {
    border-bottom: 1.5px solid rgba(128, 128, 128, 0.4);
  }

  &:focus {
    outline: none;
    border-bottom: 2px solid rgb(255, 128, 0);
  }
`;

export const SubmitButton = styled.button`
  width: 100%;
  padding: 11px 40%;
  color: #fff;
  font-size: 15px;
  font-weight: 600;
  border: none;
  border-radius: 100px 100px 100px 100px;
  cursor: pointer;
  transition: all, 240ms ease-in-out;
  background: rgb(255, 128, 0);
  
  background: linear-gradient(
    58deg,
    rgba(255, 128, 0, 1) 20%,
    rgba(255, 128, 0, 1) 100%
  );

  &:hover {
    filter: brightness(1.03);
  }
`;
